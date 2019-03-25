package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static datastorage.ResultsStorage.recordValue;

public class AsyncHttpClientHelper {

    public static void sendGet(final String url) {
        try (CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.createDefault()) {
            httpAsyncClient.start();
            final HttpGet request = new HttpGet(url);
            Future<HttpResponse> future = httpAsyncClient.execute(request, null);
            HttpEntity entity = future.get().getEntity();
            String html = EntityUtils.toString(entity);
            int diceValue = HTMLParser.getDiceSumValueFromHTML(html);

            recordValue(diceValue);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
