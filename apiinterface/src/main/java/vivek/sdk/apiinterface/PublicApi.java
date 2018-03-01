package vivek.sdk.apiinterface;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vivek.sdk.apiinterface.api.ApiService;
import vivek.sdk.apiinterface.entity.PublicUser;
import vivek.sdk.apiinterface.entity.User;

/**
 * Created by VIVEK-WO on 2018/2/28.
 */
public class PublicApi {
    private static final String BASE_URL_RELEASE = "https://api.github.com/";
    private ApiService mApiService;
    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;

    PublicApi(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        OkHttpClient okHttpClient = initOkHttpClient();
        initApiService(okHttpClient);
    }

    private OkHttpClient initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(this.readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(this.writeTimeout, TimeUnit.MILLISECONDS);
        SSLContext sslContext = initSSLContext();
        if (sslContext != null) {

            builder.sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                    .hostnameVerifier(hostnameVerifier);
        }
        return builder.build();
    }

    SSLContext initSSLContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    X509TrustManager x509TrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    private void initApiService(OkHttpClient okHttpClient) {
        if (mApiService != null) {
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_RELEASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取用户信息
     *
     * @param userName 用户名
     * @return
     */
    public Request<PublicUser> getUser(String userName) {
        Call<User> call = mApiService.getUser(userName);
        return new Request<PublicUser>().convert(new Convert<User, PublicUser>(call) {
            @Override
            PublicUser func(User user) {
                PublicUser publicUser = new PublicUser();
                publicUser.setId(user.getId());
                publicUser.setName(user.getName());
                return publicUser;
            }
        });
    }

    public static final class Builder {
        private static final int CONNECT_TIMEOUT_SECONDS = 10 * 1000;
        private static final int READ_TIMEOUT_SECONDS = 10 * 1000;
        private static final int WRITE_TIMEOUT_SECONDS = 10 * 1000;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;

        public Builder() {
            connectTimeout = CONNECT_TIMEOUT_SECONDS;
            readTimeout = READ_TIMEOUT_SECONDS;
            writeTimeout = WRITE_TIMEOUT_SECONDS;
        }

        /**
         * {@link OkHttpClient.Builder#connectTimeout(long, TimeUnit)}
         *
         * @param timeout 毫秒 {@link TimeUnit#MILLISECONDS}
         * @return
         */
        public Builder connectTimeout(int timeout) {
            connectTimeout = timeout;
            return this;
        }

        /**
         * {@link OkHttpClient.Builder#readTimeout(long, TimeUnit)}
         *
         * @param timeout 毫秒 {@link TimeUnit#MILLISECONDS}
         * @return
         */
        public Builder readTimeout(int timeout) {
            readTimeout = timeout;
            return this;
        }

        /**
         * {@link OkHttpClient.Builder#writeTimeout(long, TimeUnit)}
         *
         * @param timeout 毫秒 {@link TimeUnit#MILLISECONDS}
         * @return
         */
        public Builder writeTimeout(int timeout) {
            writeTimeout = timeout;
            return this;
        }

        public PublicApi build() {
            return new PublicApi(this);
        }
    }

}
