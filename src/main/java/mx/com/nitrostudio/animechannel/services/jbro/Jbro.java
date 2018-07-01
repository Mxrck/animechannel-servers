package mx.com.nitrostudio.animechannel.services.jbro;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.nitrostudio.animechannel.services.jbro.Exceptions.JbroException;
import mx.com.nitrostudio.animechannel.services.jbro.callbacks.ResponseCallback;
import mx.com.nitrostudio.animechannel.services.jbro.entities.Cookie;
import mx.com.nitrostudio.animechannel.services.jbro.entities.FormData;
import mx.com.nitrostudio.animechannel.services.jbro.entities.Response;
import mx.com.nitrostudio.animechannel.services.jbro.helpers.CacheManager;
import mx.com.nitrostudio.animechannel.services.jbro.helpers.CookieHandler;

public class Jbro implements Serializable {

    private static Jbro instance;

    private CacheManager cacheManager = null;

    private CookieHandler cookieHandler;
    private HashMap<String,String> headers;
    private String userAgent;
    private int timeout;
    private boolean followRedirects;
    private boolean skipCache;

    private String domain;
    private HttpURLConnection browser;
    private String url;

    private Jbro() {
        cookieHandler = new CookieHandler();
        headers = new HashMap<String,String>();
        userAgent = Jbro.USER_AGENT_CHROME;
        timeout = 3000;
        followRedirects = true;
        skipCache = false;
    }

    public static Jbro getInstance()
    {
        if (instance == null) {
            instance = new Jbro();
        }

        return instance;
    }

    public Jbro setTimeout(int timeout)
    {
        this.timeout = timeout;

        return this;
    }

    public Jbro setFollowRedirects(boolean follow)
    {
        followRedirects = follow;

        return this;
    }

    public Jbro addHeader(String name, String value)
    {
        headers.put(name, value);

        return this;
    }

    public Jbro addCookie(String name, String value)
    {
        cookieHandler.addTempCookie(name, value);

        return this;
    }

    public Jbro asyncConnect(final String url, final ResponseCallback callback)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (callback != null) {
                        callback.onStart();
                    }
                    Response response = connect(url);
                    if (callback != null) {
                        callback.onSuccess(response);
                    }
                } catch (URISyntaxException e) {
                    if (callback != null) {
                        callback.onError(new JbroException(e.getMessage()));
                    }
                } catch (IOException e) {
                    if (callback != null) {
                        callback.onError(new JbroException(e.getMessage()));
                    }
                }
            }
        });
        thread.start();

        return this;
    }

    public Response connect(String url) throws URISyntaxException, IOException
    {
        return this.connect(url, null, "GET");
    }

    public Response connect(String url, List<FormData> formDatas, String method) throws URISyntaxException, IOException
    {
        this.url = url;
        this.domain = getDomainName(url);
        Response response = null;
        url = method.equalsIgnoreCase("GET") ? this.prepareUrl(url, formDatas) : url;
        if (skipCache || cacheManager == null || !cacheManager.check(url)) {
            URL urlObject = new URL(url);
            browser = (HttpURLConnection) urlObject.openConnection();
            browser.setConnectTimeout(timeout);
            browser.setReadTimeout(timeout);
            this.setHeaders(method).setFormData(formDatas, method).setCookies();
            browser.connect();

            response = new Response(browser, this, url, domain);
            int code = response.getCode();
            if (code == 301 || code == 302) {
                return connect(response.getUrl());
            }

        }
        else {
            String filename = cacheManager.cacheFile(url);
            response = new Response(filename, this, url, domain);
        }
        clearTempData();

        return response;
    }

    private Jbro clearTempData()
    {
        headers.clear();
        cookieHandler.clearTempCookies();
        domain = null;
        browser = null;
        url = null;
        skipCache = false;

        return this;
    }

    private Jbro setHeaders()
    {
        return this.setHeaders("GET");
    }

    private Jbro setHeaders(String method)
    {
        if (browser != null) {
            try { browser.setRequestMethod(method); }
            catch (ProtocolException e) { e.printStackTrace(); }
            browser.setInstanceFollowRedirects(followRedirects);
            browser.addRequestProperty("User-Agent", userAgent);
            browser.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            for (Map.Entry<String, String> entry: headers.entrySet()) {
                browser.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return this;
    }

    private String prepareUrl(String url, List<FormData> formDatas)
    {
        StringBuilder builder = new StringBuilder(url);
        if (formDatas != null)
        {
            for(FormData data : formDatas)
            {
                if (builder.indexOf("?") >= 0)
                {
                    try
                    {
                        builder.append("&"+URLEncoder.encode(data.getName(), "UTF-8")+"="+URLEncoder.encode(data.getValue(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        builder.append("?"+URLEncoder.encode(data.getName(), "UTF-8")+"="+URLEncoder.encode(data.getValue(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return builder.toString();
    }

    private String formDataUrlEncoded(List<FormData> formDatas)
    {
        StringBuilder builder = new StringBuilder("");
        for(FormData data : formDatas)
        {
            try
            {
                builder.append("&"+URLEncoder.encode(data.getName(), "UTF-8")+"="+URLEncoder.encode(data.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (builder.length() > 0) builder.deleteCharAt(0);

        return builder.toString();
    }

    private Jbro setFormData(List<FormData> formDatas)
    {
        return this.setFormData(formDatas, "GET");
    }

    private Jbro setFormData(List<FormData> formDatas, String method)
    {
        if (browser != null) {
            if (formDatas != null && method.equalsIgnoreCase("POST"))
            {
                try
                {
                    String formdata = this.formDataUrlEncoded(formDatas);
                    byte[] postDataBytes = formdata.getBytes("UTF-8");
                    browser.setDoOutput(true);
                    browser.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    browser.getOutputStream().write(postDataBytes);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return this;
    }

    private Jbro setCookies()
    {
        if (browser != null) {
            ArrayList<Cookie> cookies = cookieHandler.getCookies(domain);
            cookies.addAll(cookieHandler.getTempCookies());
            StringBuffer cookieString = new StringBuffer();
            int iteration = 0;
            for (Cookie cookie : cookies) {
                if (iteration++ != 0) cookieString.append(" ");
                cookieString.append(cookie.getName()+"="+cookie.getValue()+";");
            }
            if (!cookieString.toString().isEmpty())
                browser.addRequestProperty("Cookie", cookieString.toString());
        }

        return this;
    }

    public CookieHandler getCookieHandler()
    {
        return cookieHandler;
    }

    public CacheManager getCacheManager()
    {
        return cacheManager;
    }

    public Jbro setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;

        return this;
    }

    private String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();

        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public Jbro identifyAsMozilla()
    {
        userAgent = Jbro.USER_AGENT_FIREFOX;

        return this;
    }

    public Jbro identifyAsSafari()
    {
        userAgent = Jbro.USER_AGENT_SAFARI;

        return this;
    }

    public Jbro identifyAsInternetExplorer()
    {
        userAgent = Jbro.USER_AGENT_IE;

        return this;
    }

    public Jbro identifyAsEdge()
    {
        userAgent = Jbro.USER_AGENT_EDGE;

        return this;
    }

    public Jbro identifyAsChrome()
    {
        userAgent = Jbro.USER_AGENT_CHROME;

        return this;
    }

    public String userAgent()
    {
        return userAgent;
    }

    final static String USER_AGENT_CHROME = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    final static String USER_AGENT_FIREFOX = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
    final static String USER_AGENT_IE = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko";
    final static String USER_AGENT_SAFARI = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A";
    final static String USER_AGENT_EDGE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246";

    public boolean isSkipCache() {
        return skipCache;
    }

    public void setSkipCache(boolean skipCache) {
        this.skipCache = skipCache;
    }
}
