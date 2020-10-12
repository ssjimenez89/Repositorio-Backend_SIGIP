package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "dvar_caract_espec")
public class VarietyCharactSpecific {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dvar_caract_espec_id_dvar_caract_espec_seq")
    @SequenceGenerator(name = "dvar_caract_espec_id_dvar_caract_espec_seq", sequenceName = "dvar_caract_espec_id_dvar_caract_espec_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_dvar_caract_espec")
    private Long id;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nvariedad")
    private Classifier classifier;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nespecificacion")
    private Specification specification;

    @NotNull
    @Column(name = "activo")
    private Boolean active = true;


}

