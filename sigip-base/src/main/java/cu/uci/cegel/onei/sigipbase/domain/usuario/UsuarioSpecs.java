package cu.uci.cegel.onei.sigipbase.domain.usuario;

import cu.uci.cegel.onei.sigipbase.web.usuario.dto.UsuarioForm;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UsuarioSpecs {
    public static Specification<Usuario> listarUsuarios(UsuarioForm usuarioForm) {
        return (root, query, builder) -> {
            Join<Object, Object> cargo = root.join("cargo", JoinType.LEFT);
            Join<Object, Object> dpa = root.join("dpa", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            if (usuarioForm.getPrimernombre() != null && usuarioForm.getPrimernombre() != "") {
                String[] nombres = usuarioForm.getPrimernombre().split(" ");
                predicates.add(builder.like(builder.lower(root.get("primernombre")), "%" + nombres[0].toLowerCase() + "%"));
                if (nombres.length == 2) {
                    predicates.add(builder.like(builder.lower(root.get("segundonombre")), "%" + nombres[1].toLowerCase() + "%"));
                }
            }
            if (usuarioForm.getPrimerapellido() != null && usuarioForm.getPrimerapellido() != "") {
                predicates.add(builder.like(builder.lower(root.get("primerapellido")), "%" + usuarioForm.getPrimerapellido().toLowerCase() + "%"));
            }
            if (usuarioForm.getSegundoapellido() != null && usuarioForm.getSegundoapellido() != "") {
                predicates.add(builder.like(builder.lower(root.get("segundoapellido")), "%" + usuarioForm.getSegundoapellido().toLowerCase() + "%"));
            }
            if (usuarioForm.getUsername() != null && usuarioForm.getUsername() != "") {
                predicates.add(builder.like(builder.lower(root.get("username")), "%" + usuarioForm.getUsername().toLowerCase() + "%"));
            }
            if (usuarioForm.getCargo() != null && usuarioForm.getCargo() != -1) {
                predicates.add(builder.equal(cargo.get("id"), usuarioForm.getCargo()));
            }
            if (usuarioForm.getDpa() != null && usuarioForm.getDpa() != -1) {
                predicates.add(builder.equal(dpa.get("id"), usuarioForm.getDpa()));
            }
            if (usuarioForm.getActivo_busqueda() != null && usuarioForm.getActivo_busqueda() != -1) {
                predicates.add(builder.equal(root.get("enabled"), usuarioForm.getActivo_busqueda() == 1));
            }

            Optional<Predicate> finalPredicate = predicates.stream().reduce(builder::and);
            return finalPredicate.orElse(null);
        };
    }
}
