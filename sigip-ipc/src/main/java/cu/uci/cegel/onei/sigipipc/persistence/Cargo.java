package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "ncargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ncargo_id_ncargo_seq")
    @SequenceGenerator(name = "ncargo_id_ncargo_seq", sequenceName = "ncargo_id_ncargo_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ncargo")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String descripcion;

    @NonNull
    @Column(name = "activo")
    private Boolean activo ;

}
