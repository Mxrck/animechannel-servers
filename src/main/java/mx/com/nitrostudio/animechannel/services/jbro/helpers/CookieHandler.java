package mx.com.nitrostudio.animechannel.services.jbro.helpers;

import mx.com.nitrostudio.animechannel.services.jbro.entities.Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CookieHandler implements Serializable {

    private HashMap<String,ArrayList<Cookie>> cookies;
    private HashMap<String,String> tempCookies;

    public CookieHandler()
    {
        tempCookies = new HashMap<String,String>();
        cookies = new HashMap<String,ArrayList<Cookie>>();
    }

    public void parseCookies(String domain, String cookiesString)
    {
        cookiesString = cookiesString.substring(0, cookiesString.indexOf(";"));
        String cookieName       = cookiesString.substring(0, cookiesString.indexOf("="));
        String cookieValue      = cookiesString.substring(cookiesString.indexOf("=") + 1, cookiesString.length());
        String cookiePath       = "";
        String cookieExpire     = "";
        Cookie cookie = getCookie(domain, cookieName);
        if (cookie == null) {
            getCookies(domain).add(new Cookie(cookieName, cookieValue, cookiePath, cookieExpire));
        }
        else {
            cookie.setName(cookieName);
            cookie.setValue(cookieValue);
            cookie.setPath(cookiePath);
            cookie.setExpires(cookieExpire);
        }
    }

    public ArrayList<Cookie> getCookies(String domain)
    {
        ArrayList<Cookie> cookiesList = cookies.get(domain);
        if (cookiesList == null) {
            cookiesList = new ArrayList<Cookie>();
            this.cookies.put(domain, cookiesList );
        }

        return cookiesList;
    }

    public Cookie getCookie(String domain, String cookieName)
    {
        ArrayList<Cookie> cookiesList = getCookies(domain);
        for (Cookie cookie :
                cookiesList) {
            if (cookie.getName().equalsIgnoreCase(cookieName))
                return cookie;
        }

        return null;
    }

    public ArrayList<Cookie> getTempCookies()
    {
        ArrayList<Cookie> tempCookiesList = new ArrayList<Cookie>();
        for(Map.Entry<String,String> entry: tempCookies.entrySet()) {
            Cookie tempCookie = new Cookie(entry.getKey(), entry.getValue(), "", "");
            tempCookiesList.add(tempCookie);
        }

        return tempCookiesList;
    }

    public CookieHandler addTempCookie(String name, String value)
    {
        tempCookies.put(name, value);

        return this;
    }

    public CookieHandler clearTempCookies()
    {
        tempCookies.clear();

        return this;
    }

}
