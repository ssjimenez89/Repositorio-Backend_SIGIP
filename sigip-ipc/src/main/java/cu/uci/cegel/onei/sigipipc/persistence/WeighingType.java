package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ntipo_ponderacion")
public class WeighingType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipo_ponderacion_id_ntipo_ponderacion_seq")
    @SequenceGenerator(name = "ntipo_ponderacion_id_ntipo_ponderacion_seq", sequenceName = "ntipo_ponderacion_id_ntipo_ponderacion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipo_ponderacion")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private boolean active = true;


    public WeighingType(String description, Boolean active) {
        this.description = description;
        this.active = active;
    }
}
