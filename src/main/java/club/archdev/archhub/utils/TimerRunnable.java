package club.archdev.archhub.utils;

import java.util.concurrent.ScheduledFuture;

public abstract class TimerRunnable implements Runnable {

    private ScheduledFuture<?> scheduledFuture;

    public abstract void running();

    @Override
    public void run() {
        try {
            if (scheduledFuture != null) {
                running();
            }
        } catch (Throwable e) {
            System.out.println("&cError while executing async timer!");
            e.printStackTrace();
        }
    }

    public void cancel() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        } else throw new NullPointerException("Scheduled future isn't set yet!");
    }

    public ScheduledFuture<?> setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;

        return scheduledFuture;
    }
}