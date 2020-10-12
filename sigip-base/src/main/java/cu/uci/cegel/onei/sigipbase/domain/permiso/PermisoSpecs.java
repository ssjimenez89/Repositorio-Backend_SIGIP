package cu.uci.cegel.onei.sigipbase.domain.permiso;

import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoForm;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermisoSpecs {
    public static Specification<Permiso> listarPermisos(PermisoForm permisoForm) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (permisoForm.getPermiso() != null && permisoForm.getPermiso() != "") {
                predicates.add(builder.like(builder.lower(root.get("permiso")), "%" + permisoForm.getPermiso().toLowerCase() + "%"));
            }

            if (permisoForm.getDescription() != null && permisoForm.getDescription() != "") {
                predicates.add(builder.like(builder.lower(root.get("description")), "%" + permisoForm.getDescription().toLowerCase() + "%"));
            }

            if (permisoForm.getActivo() != null && permisoForm.getActivo() != "") {
                predicates.add(builder.equal(builder.lower(root.get("activo")), permisoForm.isActivo()));
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(builder::and);
            return finalPredicate.orElse(null);
        };
    }
}
