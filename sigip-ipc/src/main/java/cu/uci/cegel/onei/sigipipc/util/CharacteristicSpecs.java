package cu.uci.cegel.onei.sigipipc.util;

import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CharacteristicSpecs {
        public static Specification<Characteristic> searchCharacteristic(String description) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (description != null && description != "") {
                predicates.add(builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(builder::and);
            return finalPredicate.orElse(null);
        };
    }
}
