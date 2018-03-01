package vivek.sdk.apiinterface.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vivek.sdk.apiinterface.PublicApi;
import vivek.sdk.apiinterface.Request;
import vivek.sdk.apiinterface.Response;
import vivek.sdk.apiinterface.Callback;
import vivek.sdk.apiinterface.entity.PublicUser;

/**
 * Created by VIVEK-WO on 2018/3/1.
 */

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                PublicApi publicApi = new PublicApi.Builder().build();
                Request<PublicUser> apiRequest = publicApi.getUser("vivek-wo");
//        PublicUser publicUser = apiRequest.execute();
//        System.out.println("" + publicUser);
                apiRequest.enqueue(new Callback<PublicUser>() {
                    @Override
                    public void onResponse(Request<PublicUser> request, Response<PublicUser> response) {
                        System.out.println("Response:" + response.body());
                    }

                    @Override
                    public void onFailure(Request<PublicUser> request, Throwable t) {
                        System.out.println("Throwable:" + t.getMessage());
                    }
                });
            }
        }).start();
    }
}
