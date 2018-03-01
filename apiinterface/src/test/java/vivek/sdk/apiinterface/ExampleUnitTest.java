package vivek.sdk.apiinterface;

import org.junit.Test;

import vivek.sdk.apiinterface.entity.PublicUser;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        PublicApi publicApi = new PublicApi.Builder().build();
        Request<PublicUser> request = publicApi.getUser("vivek-wo");
        PublicUser publicUser = request.execute();
        System.out.println("" + publicUser);
    }
}