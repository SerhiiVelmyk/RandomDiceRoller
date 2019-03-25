package runner;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class Executor {
    private RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();
    private ExecutorService executor;

    protected void executeDiceRoller(int poolSize, int rollCount, String url) {
        executor = new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                handler);
        Runnable worker = new Runner(url);

        IntStream.range(0, rollCount).forEach(i -> executor.execute(worker));
        executor.shutdown();

        while (!executor.isTerminated()) ;
    }
}
