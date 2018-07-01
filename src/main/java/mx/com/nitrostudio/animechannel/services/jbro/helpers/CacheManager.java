package mx.com.nitrostudio.animechannel.services.jbro.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;

public class CacheManager implements Serializable {

    private String cacheDir;
    private int duration;

    public CacheManager(String cacheDir) {
        this.cacheDir = cacheDir;
        this.duration = 10000;
    }

    public CacheManager(String cacheDir, int duration) {
        this.cacheDir = cacheDir;
        this.duration = duration;
    }

    public boolean check(String url)
    {
        return inCache(url) != null;
    }

    public String cacheFile(String url)
    {
        return inCache(url);
    }

    public CacheManager save(String url, String content)
    {
        try {
            String filename = validFilename(url);
            File file = new File(this.cacheDir+File.separator+filename);
            if (file.exists()) file.delete();
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(content);
            fileWriter.flush();
            fileWriter.close();
            printWriter.close();
        }
        catch (Exception exception) {}

        return this;
    }

    private String inCache(String url)
    {
        String filename = validFilename(url);
        File file = new File(this.cacheDir+File.separator+filename);
        if (file.exists() && file.canRead()) {
            if (file.lastModified()+this.duration > System.currentTimeMillis()) {
                return file.getPath();
            }
        }

        return null;
    }

    private String validFilename(String filename)
    {
        filename = filename.replaceAll(":", " -");

        return filename.replaceAll("[:\\\\/*\"?|<>]", "_");
    }
}
