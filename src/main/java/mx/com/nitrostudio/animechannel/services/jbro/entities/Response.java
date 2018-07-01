package mx.com.nitrostudio.animechannel.services.jbro.entities;

import mx.com.nitrostudio.animechannel.services.jbro.Jbro;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class Response {

    private HttpURLConnection browser;
    private Jbro jbro;
    private String domain;
    private String url;
    private int code;
    private StringBuffer content;
    private boolean isError;

    public Response(HttpURLConnection browser, Jbro jbro, String url, String domain)
    {
        this.browser = browser;
        this.jbro = jbro;
        this.domain = domain;
        this.url = url;
        this.content = new StringBuffer();
        this.parseData();
    }

    public Response(String cacheFile, Jbro jbro, String url, String domain)
    {
        this.jbro = jbro;
        this.domain = domain;
        this.url = url;
        this.content = new StringBuffer();
        this.parseDataFromFile(cacheFile);
    }

    private Response parseDataFromFile(String cacheFile) {
        try {
            File cache = new File(cacheFile);
            FileInputStream fileInputStream = new FileInputStream(cache);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(dataInputStream));
            String inputLine;
            while ((inputLine = buffer.readLine()) != null)   {
                content.append(inputLine);
            }
            dataInputStream.close();
        }
        catch (Exception exception) {
            content.append("");
        }

        return this;
    }

    private Response handleCookies()
    {
        if (browser != null) {
            for (Map.Entry<String, List<String>> entry: browser.getHeaderFields().entrySet()){
                String key = entry.getKey();
                if (key != null) {
                    if (key.equalsIgnoreCase("set-cookie")) {
                        List<String> cookies = entry.getValue();
                        for (String cookie : cookies) {
                            jbro.getCookieHandler().parseCookies(domain, cookie);
                        }
                    }
                }
            }
        }

        return this;
    }

    private Response parseData()
    {
        try {
            handleCookies();
            code = browser.getResponseCode();
            InputStreamReader stream = null;
            switch (code) {
                case 200:
                    isError = false;
                    stream = new InputStreamReader(browser.getInputStream());
                    break;
                case 301:
                case 302:
                    // TODO: Manejar redirección desde Jbro
                    this.url = browser.getHeaderField( "Location" );
                    break;
                default:
                    isError = true;
                    stream = new InputStreamReader(browser.getErrorStream());
            }
            if (stream != null) {
                BufferedReader bufferedReader = new BufferedReader(stream);
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    content.append(inputLine);
                }
                if (!isError) {
                    save();
                }
            }
            else {
                content.append("");
                isError = true;
            }
        }
        catch (Exception exception) {
            code = -1;
        }

        return this;
    }

    private Response save() {
        if (jbro.getCacheManager() != null) {
            jbro.getCacheManager().save(this.url, this.content.toString());
        }

        return this;
    }

    public Response get()
    {
        return this;
    }

    public int getCode()
    {
        return code;
    }

    public String getUrl()
    {
        return url;
    }

    public boolean isError()
    {
        return isError;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
