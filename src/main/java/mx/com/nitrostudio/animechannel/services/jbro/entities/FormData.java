package mx.com.nitrostudio.animechannel.services.jbro.entities;

import java.io.File;

public class FormData {

    private String name;
    private String value;
    private File file;

    public FormData(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public FormData(String name, File file)
    {
        this.name = name;
        this.file = file;
        this.value = file.getName();
    }

    public boolean isFile()
    {
        return this.file != null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
