package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ntipo_categoria")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipo_categoria_id_ntipo_categoria_seq")
    @SequenceGenerator(name = "ntipo_categoria_id_ntipo_categoria_seq", sequenceName = "ntipo_categoria_id_ntipo_categoria_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipo_categoria")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private Boolean active;
}
