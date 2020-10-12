package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResource;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;

import java.util.List;

public interface VarietyCharactSpecificService {

    List<VarietyCharactSpecific> findByVarietyCharactSpecificActive(Long classifierId);
    List<CaracteristicaEspecificacionResource> caracteristicasSpecsByVarEstabId(Long id);

    List<CaracteristicaEspecificacionResource> caracteristicasSpecsByCatchmentId(Long id);
}
