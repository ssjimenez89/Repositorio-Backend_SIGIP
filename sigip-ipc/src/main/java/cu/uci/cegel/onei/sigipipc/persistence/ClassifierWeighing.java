package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dpond_tipo_clasific")
public class ClassifierWeighing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dpond_tipo_clasific_id_npond_tipo_clasific_seq")
    @SequenceGenerator(name = "dpond_tipo_clasific_id_npond_tipo_clasific_seq", sequenceName = "dpond_tipo_clasific_id_npond_tipo_clasific_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_npond_tipo_clasific")
    private Long id;

    @NonNull
    @Column(name = "valor")
    private Double value;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nmoneda")
    private Currency currency;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_ntipo_ponderacion")
    private WeighingType weighingType;

    @ManyToOne
    @JoinColumn(name = "id_nclasificador")
    private Classifier classifier;
}
