package cu.uci.cegel.onei.classifiers.infrastructure.data;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Set4<T> implements FixedCollection<T> {
    private T[] elements;
    public final static Set4<?> EMPTY = new Set4<Object>() {
        @Override
        public Optional<Object> filter(Predicate<Object> objectPredicate) {
            return Optional.empty();
        }
    };

    @SuppressWarnings("unchecked")
    private Set4(T e1, T e2, T e3, T e4) {
        this.elements = (T[]) new Object[]{e1, e2, e3, e4};
    }

    private Set4() {

    }

    public static <T> Set4<T> of(T e1, T e2, T e3, T e4) {
        if (new HashSet<>(Arrays.asList(e1, e2, e3, e4)).size() < 4)
            throw new IllegalArgumentException("Elements in the set must not be equals");
        return new Set4<>(e1, e2, e3, e4);
    }

    public Optional<T> filter(Predicate<T> tPredicate) {
        return Stream.of(elements).filter(tPredicate).findFirst();
    }


}
