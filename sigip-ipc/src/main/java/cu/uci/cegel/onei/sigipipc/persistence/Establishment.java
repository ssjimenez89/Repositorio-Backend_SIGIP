package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "destablecimiento")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destablecimiento_id_destablecimiento_seq")
    @SequenceGenerator(name = "destablecimiento_id_destablecimiento_seq", sequenceName = "destablecimiento_id_destablecimiento_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_destablecimiento")
    Long id;

    @NonNull
    @Column(name = "cod_estab")
    String code;

    @NonNull
    @Column(name = "entidad")
    String organization;

    @NonNull
    @Column(name = "descripcion")
    String name;

    @NonNull
    @Column(name = "direccion")
    String address;

    @NonNull
    @Column(name = "nombre_contacto")
    String contact;

    @NonNull
    @Column(name = "telef_contacto")
    Integer phone;

    @Column(name = "activo")
     private boolean active = true;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nmercado")
    private MarketCurrency market;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_ntipologia")
    private Typology typology;

//    @NonNull
//    @ManyToOne
//    @JoinColumn(name = "id_nindice")
//    private OneiIndex oneiIndex;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nd_p_a")
    private DPA dpa;

    @OneToMany(mappedBy = "establishment", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @Fetch(FetchMode.SUBSELECT)
    private Collection<VarietyEstablishment> varietyEstablishments;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_nestado")
    private State state;



    public String toString() {
        return "asd";
    }

}