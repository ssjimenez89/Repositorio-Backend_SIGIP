package cu.uci.cegel.onei.sigipipc.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Builder
@Table(name = "dcaptacion")
public class Catchment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dcaptacion_id_dcaptacion_seq")
    @SequenceGenerator(name = "dcaptacion_id_dcaptacion_seq", sequenceName = "dcaptacion_id_dcaptacion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_dcaptacion")
    private Long id;

    @NonNull
    @Column(name = "fecha")
    public Date date;

    @NonNull
    @Column(name = "usuario")
    private Long users;

    @NonNull
    @Column(name = "precio")
    private Float price;

    @NonNull
    @Column(name = "relativo")
    private Float relative;

    @NonNull
    @Column(name = "imputado")
    private Boolean imputed;

    @NonNull
    @Column(name = "id_variedad")
    private Long idVariedad;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nincidencia")
    private Incidence incidence;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_establecimiento")
    private Establishment establishment;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nobservacion")
    private Observation observation;

    @NonNull
    @OneToOne
    @JoinColumn(name = "id_nmoneda")
    private Currency currency;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_unidad_medida")
    private Specification unidadMedida;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_cantidad")
    private Specification cantidad;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_clasificador")
    private Classifier classifier;

//    @JoinTable(name = "dcaptacion_dvar_estab", joinColumns = {
//            @JoinColumn(name = "id_dcaptacion", referencedColumnName = "id_dcaptacion")}, inverseJoinColumns = {
//            @JoinColumn(name = "id_dvar_estab", referencedColumnName = "id_dvar_estab")})
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<VarietyEstablishment> dvarEstabList;

    @JoinTable(name = "dcaptacion_dvar_caract_espec", joinColumns = {
            @JoinColumn(name = "id_dcaptacion", referencedColumnName = "id_dcaptacion")}, inverseJoinColumns = {
            @JoinColumn(name = "id_dvar_caract_espec", referencedColumnName = "id_dvar_caract_espec")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<VarietyCharactSpecific> dVarCaracEspec;

    @Column(name = "fuera_rango")
    private Boolean fueraRango;

    public String toString() {
        return "Captacion: " + id.toString();
    }

}
