package runner;

import static utils.AsyncHttpClientHelper.sendGet;

public class Runner implements Runnable {
    private final String url;

    Runner(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        sendGet(url);
    }
}
