package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

//import lombok.Data;
//import lombok.NonNull;
//import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dplanificacion_fecha")
public class PlanningDate {

    @NonNull
    @Column(name = "fecha")
    public LocalDate date;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dplanificacion_fecha_id_plan_date_seq")
    @SequenceGenerator(name = "dplanificacion_fecha_id_plan_date_seq", sequenceName = "dplanificacion_fecha_id_plan_date_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_plan_date")
    private Long id;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "id_nplanificacion")
    private Planning planning;
}
