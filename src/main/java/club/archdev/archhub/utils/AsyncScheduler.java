package club.archdev.archhub.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

@UtilityClass
public class AsyncScheduler {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4,
            new ThreadFactoryBuilder().setNameFormat("Schedule CascadiaMC Thread %d").build());

    /**
     * Run a task asynchronously.
     */
    public Future<?> run(Runnable runnable) {
        return scheduler.submit(runnable);
    }

    /**
     * Run a task after scheduled delay asynchronously.
     */
    public ScheduledFuture<?> later(Runnable runnable, long delay, TimeUnit time) {
        return scheduler.schedule(runnable, delay, time);
    }

    /**
     * Run a task in a fixed rate asynchronously.
     */
    public ScheduledFuture<?> timer(TimerRunnable runnable, long delay, long period, TimeUnit time) {
        return runnable.setScheduledFuture(scheduler.scheduleAtFixedRate(runnable, delay, period, time));
    }
}