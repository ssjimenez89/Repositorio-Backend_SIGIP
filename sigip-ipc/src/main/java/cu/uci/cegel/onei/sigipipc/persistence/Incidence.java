package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "nincidencia")
public class Incidence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nincidencia_id_nincidencia_seq")
    @SequenceGenerator(name = "nincidencia_id_nincidencia_seq", sequenceName = "nincidencia_id_nincidencia_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nincidencia")
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

}
