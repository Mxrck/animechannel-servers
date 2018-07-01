package mx.com.nitrostudio.animechannel.models.entities.servers.hosts;

import mx.com.nitrostudio.animechannel.models.entities.servers.GenericServer;
import mx.com.nitrostudio.animechannel.models.entities.servers.IServer;
import mx.com.nitrostudio.animechannel.models.webservice.ICallback;
import mx.com.nitrostudio.animechannel.services.jbro.Jbro;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fire extends GenericServer implements IServer {

    @Nullable
    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public boolean isProcessable() {
        return true;
    }

    @Override
    public Thread process(final ICallback<String> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (callback != null) callback.onStart();
                Jbro http = Jbro.getInstance();
                boolean tempCache = http.isSkipCache();
                http.setSkipCache(true);
                if (getDirectURL() != null && !getDirectURL().isEmpty() && callback != null) callback.onSuccess(getDirectURL());
                try {
                    String responseMediafire = http.connect(getURL()).get().toString();
                    Pattern patternVideo = Pattern.compile("[\"']http://download(.*?)[\"']");
                    Matcher matcher = patternVideo.matcher(responseMediafire);
                    if (matcher.find())
                    {
                        String video_url = matcher.group(1);
                        if (video_url != null && !video_url.isEmpty()) setDirectUrl("http://download"+video_url);
                    }
                }
                catch (Exception exception)
                {
                    // Crashlytics.logException(exception);
                    exception.printStackTrace();
                }
                http.setSkipCache(tempCache);
                if (getDirectURL() != null && !getDirectURL().isEmpty() && callback != null) callback.onSuccess(getDirectURL());
                else
                if (callback != null) callback.onError("No se pudo obtener el video");
            }
        });
        thread.start();
        return thread;
    }
}