package cu.uci.cegel.onei.sigipipc.persistence;


import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nmoneda")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nmoneda_id_nmoneda_seq")
    @SequenceGenerator(name = "nmoneda_id_nmoneda_seq", sequenceName = "nmoneda_id_nmoneda_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nmoneda")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @Column(name = "siglas")
    private String acronym;

    @NonNull
    @Column(name = "activo")
    private Boolean active = true;

}
