package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ntipo_unidad_medida")
public class MeasurementUnitType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipo_unidad_medida_id_ntipo_unidad_medida_seq")
    @SequenceGenerator(name = "ntipo_unidad_medida_id_ntipo_unidad_medida_seq", sequenceName = "ntipo_unidad_medida_id_ntipo_unidad_medida_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipo_unidad_medida")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private Boolean active;

}
