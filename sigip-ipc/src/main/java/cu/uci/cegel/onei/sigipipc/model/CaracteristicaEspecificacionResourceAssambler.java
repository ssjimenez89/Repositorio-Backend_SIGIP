package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaracteristicaEspecificacionResourceAssambler {
    @Autowired
    BasicStringAndIdResourceAssambler basicStringAndIdResourceAssambler;
    public CaracteristicaEspecificacionResource toResource(BasicStringAndIdResource carac, BasicStringAndIdResource spec) {
        return CaracteristicaEspecificacionResource.builder().
                spec(spec).
                carac(carac).
                build();
    }

    public List<CaracteristicaEspecificacionResource> toResource(List<VarietyCharactSpecific> list) {
        return list.stream().map(variedadCaractEspec -> {
            Specification espesificacion = variedadCaractEspec.getSpecification();
            Characteristic caracteritica = espesificacion.getCharacteristic();
            String caracDesc = caracteritica != null ? espesificacion.getCharacteristic().getDescription() : "-";
            Long tipoUM = espesificacion.getMeasurementUnitType() != null ? espesificacion.getMeasurementUnitType().getId(): null;
            BasicStringAndIdResource spec = basicStringAndIdResourceAssambler.toResource(espesificacion.getDescription(),espesificacion.getId(),tipoUM);
            BasicStringAndIdResource carac = basicStringAndIdResourceAssambler.toResource(caracDesc,caracteritica.getId(),null);
            return toResource(carac, spec);
        }).collect(Collectors.toList());

    }
}
