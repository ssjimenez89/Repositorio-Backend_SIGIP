package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@Table(name = "nplanificacion")
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nplanificacion_id_nplanificacion_seq")
    @SequenceGenerator(name = "nplanificacion_id_nplanificacion_seq", sequenceName = "nplanificacion_id_nplanificacion_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nplanificacion")
    private Long id;

    @NonNull
    @Column(name = "semana")
    private int week;

    @NonNull
    @Column(name = "dia")
    private String day;

    @Column(name = "id_nindice")
    private int index;



    @NotNull
    @OneToMany(mappedBy = "planning", fetch = FetchType.EAGER)
    private Collection<PlanningDate> dates;

    /*public Planning(int week, String day, OneiIndex index, Collection<Establishment> establishments) {
        this.week = week;
        this.day = day;
        this.index = index;
        this.establishments = establishments;
    }*/

    @NotNull
    @OneToMany(mappedBy = "planning", fetch = FetchType.LAZY)
    private Collection<VarietyEstablishment> varietyEstablishments;

    public Planning(int week, String day) {
        this.week = week;
        this.day = day;
    }
}