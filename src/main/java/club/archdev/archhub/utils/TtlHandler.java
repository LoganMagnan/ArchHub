package club.archdev.archhub.utils;

public interface TtlHandler<E> {

    void onExpire(E element);

    long getTimestamp(E element);
}
