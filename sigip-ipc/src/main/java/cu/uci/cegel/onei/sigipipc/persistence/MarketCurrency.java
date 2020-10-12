package cu.uci.cegel.onei.sigipipc.persistence;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "nmercado_moneda")
public class MarketCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nmercado_moneda_id_nmercado_moneda_seq")
    @SequenceGenerator(name = "nmercado_moneda_id_nmercado_moneda_seq", sequenceName = "nmercado_moneda_id_nmercado_moneda_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id_nmercado_moneda")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_nmoneda")
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "id_nmercado")
    private Market market;


}
