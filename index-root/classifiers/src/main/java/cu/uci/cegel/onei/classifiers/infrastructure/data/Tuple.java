package cu.uci.cegel.onei.classifiers.infrastructure.data;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Tuple<T> implements FixedCollection<T> {
    private T first;
    private T second;
    private static Tuple<?> EMPTY = new Tuple<>() ;

    @SuppressWarnings("unchecked")
    private Tuple(T e1, T e2) {
        first = e1; second = e2;
    }

    private Tuple() {

    }

    public static <T> Tuple<T> of(T e1, T e2) {
        if (e1.equals(e2))
            throw new IllegalArgumentException("Elements in the tuple must not be equals");
        return new Tuple<>(e1, e2);
    }

    @SuppressWarnings("unchecked")
    public static <T> Tuple<T> empty() {
        return (Tuple<T>) EMPTY;
    }

    public Optional<T> filter(Predicate<T> tPredicate) {
        return Stream.of(first, second).filter(tPredicate).findAny();
    }


}
