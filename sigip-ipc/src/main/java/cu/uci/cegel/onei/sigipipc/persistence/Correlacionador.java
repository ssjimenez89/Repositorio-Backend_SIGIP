package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Table(name = "ncorrelacionador")
public class Correlacionador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ncorrelacionador_id_ncorrelacionador_seq")
    @SequenceGenerator(name = "ncorrelacionador_id_ncorrelacionador_seq", sequenceName = "ncorrelacionador_id_ncorrelacionador_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_ncorrelacionador")
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_um_base")
    private Specification umBase;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_um_a_convertir")
    private Specification umConvertir;

    @NonNull
    @Column(name = "relacion")
    private Float relacion;

}
