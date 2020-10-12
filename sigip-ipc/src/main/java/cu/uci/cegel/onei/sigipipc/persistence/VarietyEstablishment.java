package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Data
@Table(name = "dvar_estab")
public class VarietyEstablishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dvar_estab_id_dvar_estab_seq")
    @SequenceGenerator(name = "dvar_estab_id_dvar_estab_seq", sequenceName = "dvar_estab_id_dvar_estab_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_dvar_estab")
    private Long id;

    @Column(name = "activo")
    private boolean active = true;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_planificacion")
    private Planning planning;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_destablecimiento")
    private Establishment establishment;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nestado")
    private State state;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_variedad")
    private Classifier classifier;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_unidad_medida")
    private Specification unidadMedida;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_cantidad")
    private Specification cantidad;


    //@NonNull
//    @OneToOne
//    @JoinColumn(name = "id_dcaptacion")
//    private Catchment catchment;

//    @ManyToMany(mappedBy = "dvarEstabList")
//    private List<Catchment> dcaptacionList;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "dvar_estab_dvar_caract_espec", joinColumns = {
            @JoinColumn(name = "id_dvar_estab", referencedColumnName = "id_dvar_estab")}, inverseJoinColumns = {
            @JoinColumn(name = "id_dvar_caract_espec", referencedColumnName = "id_dvar_caract_espec")})
    private List<VarietyCharactSpecific> varietyCharactSpecifics;

    public String toString() {
        return "asd";
    }

}
