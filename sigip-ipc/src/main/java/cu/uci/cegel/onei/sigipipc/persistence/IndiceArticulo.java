package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "dindice_articulo")
public class IndiceArticulo {

    @Id
    @Column(name = "id_dindice")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dindice_articulo_id_dindice_articulo_seq")
    @SequenceGenerator(name = "dindice_articulo_id_dindice_articulo_seq", sequenceName = "dindice_articulo_id_dindice_articulo_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "id_variedad")
    private Long idvariedad;

    @NonNull
    @Column(name = "indice")
    private Float indice;

    @NonNull
    @Column(name = "fecha")
    private Date fecha;

    @JoinTable(name = "dindice_articulo_dvar_caract_esp", joinColumns = {
            @JoinColumn(name = "id_dindice", referencedColumnName = "id_dindice")}, inverseJoinColumns = {
            @JoinColumn(name = "id_dvar_caract_espec", referencedColumnName = "id_dvar_caract_espec")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<VarietyCharactSpecific> dVarCaracEspec;

    @Column(name = "id_mun")
    private Long idmunicipio;

    @Column(name = "id_prov")
    private Long idprovincia;

    public String toString(){
        return "Índice Artículo: " + id.toString();
    }
}
