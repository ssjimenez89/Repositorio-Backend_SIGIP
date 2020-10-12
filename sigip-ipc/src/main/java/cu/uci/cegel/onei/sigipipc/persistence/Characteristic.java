package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ncaracteristica")
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ncaracteristica_id_ncaracteristica_seq")
    @SequenceGenerator(name = "ncaracteristica_id_ncaracteristica_seq", sequenceName = "ncaracteristica_id_ncaracteristica_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ncaracteristica")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @NonNull
    @ColumnDefault("true")
    @Column(name = "activo")
    private Boolean active = true;

    @OneToMany(mappedBy = "characteristic", fetch = FetchType.LAZY)
    private List<Specification> specifications;




}
