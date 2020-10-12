package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nindice")
public class OneiIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nindice_id_nindice_seq")
    @SequenceGenerator(name = "nindice_id_nindice_seq", sequenceName = "nindice_id_nindice_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nindice")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @Column(name = "activo")
    private Boolean active = true;

//    @OneToMany(mappedBy = "oneiIndex", fetch = FetchType.EAGER)
//    private Collection<Classifier> classifiers;
//
//    @OneToMany(mappedBy = "index", fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
//    private Collection<Planning> plannings;

}
