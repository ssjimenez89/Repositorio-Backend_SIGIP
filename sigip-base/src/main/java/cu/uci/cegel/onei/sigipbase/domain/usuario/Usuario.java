package cu.uci.cegel.onei.sigipbase.domain.usuario;

import cu.uci.cegel.onei.sigipbase.domain.cargo.Cargo;
import cu.uci.cegel.onei.sigipbase.domain.dpa.Dpa;
import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.FechaConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author cegel
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dusuario", schema = "arquitectura")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dusuario_seq")
    @SequenceGenerator(name = "dusuario_seq", schema = "arquitectura",  sequenceName = "dusuario_id_seq", initialValue = 1, allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "primernombre")
    private String primernombre;

    @Column(name = "segundonombre")
    private String segundonombre;

    @Basic(optional = false)
    @Column(name = "primerapellido")
    private String primerapellido;

    @Column(name = "segundoapellido")
    private String segundoapellido;

    @Basic(optional = false)
    @Column(name = "fechainicio")
    @Convert(converter = FechaConverter.class)
    private LocalDate fechainicio;

    @Column(name = "fechafin")
    @Convert(converter = FechaConverter.class)
    private LocalDate fechafin;

    @Column(name = "carnetidentidad")
    private String carnetidentidad;

    @Column(name = "correo")
    private String correo;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    private boolean enabled;

    @Basic(optional = false)
    @Column(name = "non_credential_expired")
    private boolean credentialsexpired;

    @Column(name = "non_expired")
    private boolean expired;

    @Basic(optional = false)
    @Column(name = "non_locked")
    private boolean locked;

    @ManyToOne
    @JoinColumn(name = "dpa")
    private Dpa dpa;

    @ManyToOne
    @JoinColumn(name = "cargo")
    private Cargo cargo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "dusuario_permiso", schema = "arquitectura",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id"))
    private Set<Permiso> permisos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "dusuario_rol", schema = "arquitectura",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Set<Rol> roles;

    @Transient
    public String getNombreCompleto() {
        return new StringBuilder()
                .append(primernombre)
                .append(segundonombre != null ? " " + segundonombre + " " : " ")
                .append(primerapellido)
                .append(segundoapellido != null ? " " + segundoapellido : "")
                .toString();
    }
}
