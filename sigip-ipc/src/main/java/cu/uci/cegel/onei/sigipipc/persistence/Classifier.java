package cu.uci.cegel.onei.sigipipc.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "nclasificador")
@NoArgsConstructor
@RequiredArgsConstructor
public class Classifier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nclasificador_id_nclasificador_seq")
    @SequenceGenerator(name = "nclasificador_id_nclasificador_seq", sequenceName = "nclasificador_id_nclasificador_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nclasificador")
    private Long id;

    @NonNull
    @Column(name = "descripcion")
    private String description;

    @Column(name = "activo")
    private Boolean active = true;

    @NonNull
    @Unique
    @Column(name = "codigo")
    private String code;

    @OneToMany(mappedBy = "parent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    private Collection<Classifier> childrens;


    @JoinColumn(name = "id_padre", referencedColumnName = "id_nclasificador")
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier parent;

    @OneToMany(mappedBy = "classifier", cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    private Collection<ClassifierWeighing> classifierWeighings;

    //@NonNull
    @ManyToOne
    @JoinColumn(name = "id_tipoclasificador")
    private ClassifierType classifierType;

    @ManyToOne
    @JoinColumn(name = "id_ntipo_variedad")
    private VarietyType varietyType;

    @ManyToMany
    @JoinTable(name = "nclasificador_mercado_moneda",
            joinColumns = @JoinColumn(name = "id_nclasificador"),
            inverseJoinColumns = @JoinColumn(name = "id_nmercado_moneda"))
    private Collection<MarketCurrency> marketCurrencies;

    @OneToMany(mappedBy = "classifier", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    private Collection<VarietyCharactSpecific> varietyCharactSpecifics;

    public int getLevel() {
        return this.getParent() == null ? 1 : this.getParent().getLevel() + 1;
    }

    public List<VarietyCharactSpecific> varietyCharactSpecificsActive() {
        return this.getVarietyCharactSpecifics().stream().filter(elm -> elm.getActive()).collect(Collectors.toList());
    }

    @Transient
    private Collection<VarietyCharactSpecific> varietyCharactSpecificsActives;
}
