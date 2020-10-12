package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "nd_p_a")
public class DPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nd_p_a_id_nd_p_a_seq")
    @SequenceGenerator(name = "nd_p_a_id_nd_p_a_seq", sequenceName = "nd_p_a_id_nd_p_a_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nd_p_a")
    private Long id;

    @NonNull
    @Column(name = "cod_d_p_a")
    private String code;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private boolean active = true;

    @ManyToOne
    //@NonNull  Verificar!!!
    @JoinColumn(name = "id_nregion")
    private Region region;

    @JoinColumn(name = "idpadre", referencedColumnName = "id_nd_p_a")
    @ManyToOne
    private DPA parent;

    @OneToMany(mappedBy = "parent")
    private Collection<DPA> childrens;

    public DPA(String code, String description, boolean active, Region region) {
        this.code = code;
        this.description = description;
        this.active = active;
        this.region = region;
    }

    public DPA(String code, String description, boolean active) {
        this.code = code;
        this.description = description;
        this.active = active;
    }

}