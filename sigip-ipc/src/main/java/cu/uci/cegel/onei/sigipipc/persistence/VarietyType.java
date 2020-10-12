package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
@Entity
@Data
@Table(name = "ntipo_variedad")
public class VarietyType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipo_variedad_id_ntipo_variedad_seq")
    @SequenceGenerator(name = "ntipo_variedad_id_ntipo_variedad_seq", sequenceName = "ntipo_variedad_id_ntipo_variedad_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipo_variedad")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private boolean active = true;

    public VarietyType(String description, Boolean active) {
        this.description = description;
        this.active = active;
    }
}