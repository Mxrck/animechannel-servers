package mx.com.nitrostudio.animechannel.models.webservice;

public interface ICallback<T> {

    public void onSuccess(T response);
    public void onError(String message);
    public void onStart();

}
