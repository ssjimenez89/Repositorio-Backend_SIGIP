package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nobservacion")
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nobservacion_id_nobservacion_seq")
    @SequenceGenerator(name = "nobservacion_id_nobservacion_seq", sequenceName = "nobservacion_id_nobservacion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nobservacion")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "siglas")
    private String acronym;

    @NonNull
    @Column(name = "activo")
    private Boolean active;

//    @OneToMany(mappedBy = "observation")
//    private List<Catchment> dcaptacionList;


    public String toString() {
        return "asd";
    }

}
