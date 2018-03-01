package vivek.sdk.apiinterface.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vivek.sdk.apiinterface.entity.User;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */
public interface ApiService {

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String userName);

}
