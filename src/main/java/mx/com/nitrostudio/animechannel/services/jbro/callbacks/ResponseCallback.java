package mx.com.nitrostudio.animechannel.services.jbro.callbacks;

import mx.com.nitrostudio.animechannel.services.jbro.Exceptions.JbroException;
import mx.com.nitrostudio.animechannel.services.jbro.entities.Response;

public interface ResponseCallback {

    void onSuccess(Response response);
    void onStart();
    void onError(JbroException exception);

}
