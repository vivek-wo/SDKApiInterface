package vivek.sdk.apiinterface;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */

public interface Callback<T> {
    /**
     * Invoked for a received HTTP response.
     * <p/>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     */
    void onResponse(Request<T> request, Response<T> response);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    void onFailure(Request<T> request, Throwable t);
}