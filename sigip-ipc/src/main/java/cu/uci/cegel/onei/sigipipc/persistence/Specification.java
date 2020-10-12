package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Builder
@Table(name = "nespecificacion")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nespecificacion_id_nespecificacion_seq")
    @SequenceGenerator(name = "nespecificacion_id_nespecificacion_seq", sequenceName = "nespecificacion_id_nespecificacion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nespecificacion")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private Boolean active = true;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_ncaracteristica")
    private Characteristic characteristic;

    @ManyToOne
    @JoinColumn(name = "id_ntipo_unidad_medida")
    private MeasurementUnitType measurementUnitType;

    @OneToMany(mappedBy = "specification")
    private Collection<VarietyCharactSpecific> varietyCharactSpecifics;


}
