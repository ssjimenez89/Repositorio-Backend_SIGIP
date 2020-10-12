package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import cu.uci.cegel.onei.sigipipc.persistence.Incidence;
import cu.uci.cegel.onei.sigipipc.persistence.Observation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CaptacionResource {
    //propiedades directas de la captacion
    Long id;
    Float precio;
    Boolean imputed;
    Currency currency;
    Long user;
    LocalDate fecha;
    //propiedades a traves del nomenclador incidencia
    String incidenceName;
    Long incidenceId;
    //join con dpa
    String dpaCod;
    String dpaDesc;
    String dpaId;
    DPA dpa;
    Incidence incidencia;
    Observation observacion;

    //a traves del establecimiento
    Long establishmentId;
    String establishmentName;
    // join con mercado
    String marketDescription;
    //join con variedad-esta
    String varietyName;
    Long varietyId;
    //join con specificacion
    List<String> specifications;
    //join con caracteristicas
    List<String> caracteristicas;
    Long total;
    List<CaracteristicaEspecificacionResource> specs;
    Float precioMesAnterior;
    String tipologiaName;
    Float relativo;
    Long varEstabId;

    BasicStringAndIdResource cant;
    BasicStringAndIdResource unidadMedida;


    Float promedioPreciosAgropecuario;
    Float promedioPreciosAgropecuarioAnterior;
    Float calculoRelativoVar_Est;
    Float calculoArticuloNacion;
}
