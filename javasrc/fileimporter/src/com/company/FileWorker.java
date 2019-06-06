package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FileWorker implements Runnable {
    private String url;
    private String dest;

    boolean getFile(String url, String where)
    {
        URL website = null;
        try {
            website = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        Path p = Paths.get(website.getFile());
        String fileName = p.getFileName().toString();
        String path = where + fileName;
        ReadableByteChannel rbc;
        FileOutputStream fos = null;
        try  {
            FileRecord fr = new FileRecord();
            fr.fileName = path;
            fr.remotePath = url;
            rbc = Channels.newChannel(website.openStream());
            fos = new FileOutputStream(path);
            fr.startDownload = LocalDateTime.now();
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            fr.endDownload = LocalDateTime.now();
            File file = new File(path);
            fr.fileSize = file.length();
            DBWorker.insert(fr.fileName, fr.remotePath, fr.fileSize, fr.startDownload, fr.endDownload);

        }catch (IOException e) {
            return false;
        }
        return true;
    }

    public FileWorker(String from, String where)
    {
        url =from;
        dest = where;
    }
    @Override
    public void run() {
        getFile(url, dest);
    }
}
