package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;import java.util.Date;
import java.util.List;
@Entity
@Data
@Table(name = "dvar_caract_espec")
@XmlRootElement
public class VariedadCaractEspec implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dvar_caract_espec")
    private Integer idDvarCaractEspec;

//    @OneToMany(mappedBy = "idDvarCaractEspec")
//    private List<DvarEstab> dvarEstabList;

    @JoinColumn(name = "id_ncaracteristica", referencedColumnName = "id_ncaracteristica")
    @ManyToOne
    private Characteristic idNcaracteristica;

    @JoinColumn(name = "id_nvariedad", referencedColumnName = "id_nclasificador")
    @ManyToOne
    private Classifier idNvariedad;

    @JoinColumn(name = "id_nespecificacion", referencedColumnName = "id_nespecificacion")
    @ManyToOne
    private Specification idNespecificacion;
}
