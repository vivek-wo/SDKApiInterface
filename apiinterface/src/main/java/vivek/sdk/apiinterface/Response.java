package vivek.sdk.apiinterface;

import okhttp3.Headers;
import okhttp3.ResponseBody;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */

public class Response<T> {
    private final okhttp3.Response rawResponse;
    private final T body;
    private final ResponseBody errorBody;

    Response(okhttp3.Response rawResponse, T body, ResponseBody errorBody) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.errorBody = errorBody;
    }

    /**
     * The raw response from the HTTP client.
     */
    public okhttp3.Response raw() {
        return rawResponse;
    }

    /**
     * HTTP status code.
     */
    public int code() {
        return rawResponse.code();
    }

    /**
     * HTTP status message or null if unknown.
     */
    public String message() {
        return rawResponse.message();
    }

    /**
     * HTTP headers.
     */
    public Headers headers() {
        return rawResponse.headers();
    }

    /**
     * Returns true if {@link #code()} is in the range [200..300).
     */
    public boolean isSuccessful() {
        return rawResponse.isSuccessful();
    }

    /**
     * The deserialized response body of a {@linkplain #isSuccessful() successful} response.
     */
    public T body() {
        return body;
    }

    /**
     * The raw response body of an {@linkplain #isSuccessful() unsuccessful} response.
     */
    public ResponseBody errorBody() {
        return errorBody;
    }
}
