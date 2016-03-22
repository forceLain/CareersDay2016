package ru.eastbanctech.careeersday.android;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WebApiTests {

    @Test
    public void listOfBulbs() throws IOException {
        WebService webService = new WebService(RuntimeEnvironment.application);
        Call<Map<String, Boolean>> call = webService.getWebApi().list();
        Response<Map<String, Boolean>> response = call.execute();
        Map<String, Boolean> list = response.body();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    public void switchBulbs() throws IOException {
        WebService webService = new WebService(RuntimeEnvironment.application);
        Call<Boolean> call = webService.getWebApi().switchBulb("light01", true);
        Response<Boolean> response = call.execute();
        Boolean body = response.body();
        assertTrue(body);
    }
}
