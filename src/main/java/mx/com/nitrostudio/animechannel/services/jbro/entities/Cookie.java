package mx.com.nitrostudio.animechannel.services.jbro.entities;

public class Cookie {

    private String name;
    private String value;
    private String path;
    private String expires;

    public Cookie(String name, String value, String path, String expires) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

}
