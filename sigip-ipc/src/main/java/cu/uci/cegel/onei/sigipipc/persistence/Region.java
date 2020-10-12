package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "nregion")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nregion_id_nregion_seq")
    @SequenceGenerator(name = "nregion_id_nregion_seq", sequenceName = "nregion_id_nregion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nregion")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String descripcion;

    @NonNull
    @Column(name = "activo")
    private boolean activo;

}
