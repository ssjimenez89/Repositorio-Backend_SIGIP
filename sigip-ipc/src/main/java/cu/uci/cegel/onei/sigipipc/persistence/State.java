package cu.uci.cegel.onei.sigipipc.persistence;


import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "nestado")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nestado_id_nestado_seq")
    @SequenceGenerator(name = "nestado_id_nestado_seq", sequenceName = "nestado_id_nestado_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nestado")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String descripcion;

    @NonNull
    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "state")
    private List<VarietyEstablishment> varietyEstablishmentList;

    @OneToMany(mappedBy = "state")
    private List<Establishment> establishments;

}
