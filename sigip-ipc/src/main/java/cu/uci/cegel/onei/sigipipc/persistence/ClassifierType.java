package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ntipo_clasific")
public class ClassifierType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ntipo_clasific_id_ntipo_clasific_seq")
    @SequenceGenerator(name = "ntipo_clasific_id_ntipo_clasific_seq", sequenceName = "ntipo_clasific_id_ntipo_clasific_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ntipo_clasific")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "activo")
    private boolean active = true;

    @NonNull
    @Column(name = "cod_clasific")
    private String codeClassifier;

    public ClassifierType(String description, boolean active, String codeClassifier) {
        this.description = description;
        this.active = active;
        this.codeClassifier = codeClassifier;
    }
}
