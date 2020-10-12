package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ntipologia")
public class Typology {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipologia_id_ntipologia_seq")
    @SequenceGenerator(name = "ntipologia_id_ntipologia_seq", sequenceName = "ntipologia_id_ntipologia_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipologia")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "imputada")
    private Boolean imputed;

    @NonNull
    @Column(name = "activo")
    private Boolean active;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_ntipo_categoria")
    private Category category;


    public Typology(String description, Category category, Boolean active, Boolean imputed) {
        this.description = description;
        this.category = category;
        this.active = active;
        this.imputed = imputed;
    }
}
