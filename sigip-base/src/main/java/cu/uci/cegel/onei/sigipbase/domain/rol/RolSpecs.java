package cu.uci.cegel.onei.sigipbase.domain.rol;

import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolForm;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolSpecs {
    public static Specification<Rol> listarRoles(RolForm rolForm) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (rolForm.getRol() != null && rolForm.getRol() != "") {
                predicates.add(builder.like(builder.lower(root.get("rol")), "%" + rolForm.getRol().toLowerCase() + "%"));
            }
            Optional<Predicate> finalPredicate = predicates.stream().reduce(builder::and);
            return finalPredicate.orElse(null);
        };
    }
}
