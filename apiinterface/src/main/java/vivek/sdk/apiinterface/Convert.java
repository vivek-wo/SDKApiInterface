package vivek.sdk.apiinterface;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */
public class Convert<T, R> {

    Call<T> call;

    Convert(Call<T> call) {
        this.call = call;
    }

    /**
     * {@link Call#execute()}
     *
     * @return
     * @throws IOException
     */
    R execute() throws IOException {
        return func(call.execute().body());
    }

    /**
     * {@link Call#enqueue(retrofit2.Callback)}
     *
     * @param request
     * @param callback
     */
    void enqueue(final Request<R> request, final Callback<R> callback) {
        this.call.enqueue(new retrofit2.Callback<T>() {

            @Override
            public void onResponse(Call<T> call, retrofit2.Response<T> response) {
                callback.onResponse(request, new Response<R>(response.raw(), func(response.body()), response.errorBody()));
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFailure(request, t);
            }
        });
    }

    /**
     * {@link Call#cancel()}
     */
    void cancel() {
        this.call.cancel();
    }

    /**
     * {@link Call#isCanceled()}
     *
     * @return
     */
    boolean isCanceled() {
        return this.call.isCanceled();
    }

    /**
     * This method runs based on {@link Request#execute()} and {@link Request#enqueue(Callback)}
     * ,So it's best not to perform too much time consuming.
     *
     * @param t
     * @return
     */
    R func(T t) {
        return (R) t;
    }

}
