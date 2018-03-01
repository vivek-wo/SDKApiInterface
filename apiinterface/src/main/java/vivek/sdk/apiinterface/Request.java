package vivek.sdk.apiinterface;

import java.io.IOException;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */
public class Request<R> {

    Convert<?, R> convert;

    Request() {

    }

    Request<R> convert(Convert<?, R> convert) {
        this.convert = convert;
        return this;
    }

    /**
     * Synchronously send the request and return its response.
     *
     * @return
     * @throws IOException
     */
    public R execute() throws IOException {
        return convert.execute();
    }

    /**
     * Asynchronously send the request and notify {@code callback} of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     *
     * @param callback
     */
    public void enqueue(Callback<R> callback) {
        convert.enqueue(this, callback);
    }

    /**
     * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
     * yet been executed it never will be.
     */
    public void cancel() {
        convert.cancel();
    }

    /**
     * True if {@link #cancel()} was called.
     *
     * @return
     */
    public boolean isCanceled() {
        return convert.isCanceled();
    }

}
