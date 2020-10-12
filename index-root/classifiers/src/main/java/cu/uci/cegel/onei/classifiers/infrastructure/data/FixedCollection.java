package cu.uci.cegel.onei.classifiers.infrastructure.data;

import java.util.Optional;
import java.util.function.Predicate;

public interface FixedCollection<T> {

    static FixedCollection empty() {
        return tPredicate -> Optional.empty();
    }

    Optional<T> filter(Predicate<T> tPredicate);
}
