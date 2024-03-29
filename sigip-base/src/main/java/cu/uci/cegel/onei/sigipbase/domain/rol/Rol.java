package cu.uci.cegel.onei.sigipbase.domain.rol;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author cegel
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drol", schema = "arquitectura")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drol_seq")
    @SequenceGenerator(name = "drol_seq", schema = "arquitectura", sequenceName = "drol_id_seq", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;
    @Column(name = "description")
    private String description;
    @Column(name = "activo")
    private Boolean activo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dpermiso_rol", schema = "arquitectura",
            joinColumns = {@JoinColumn(name = "rol_id")},
            inverseJoinColumns = {@JoinColumn(name = "permiso_id")})
    private Set<Permiso> permisos;
}
