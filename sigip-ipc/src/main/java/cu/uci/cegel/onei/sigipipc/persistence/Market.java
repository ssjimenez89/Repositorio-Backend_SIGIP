package cu.uci.cegel.onei.sigipipc.persistence;


import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "nmercado")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nmercado_id_nmercado_seq")
    @SequenceGenerator(name = "nmercado_id_nmercado_seq", sequenceName = "nmercado_id_nmercado_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nmercado")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private Boolean active;

}
