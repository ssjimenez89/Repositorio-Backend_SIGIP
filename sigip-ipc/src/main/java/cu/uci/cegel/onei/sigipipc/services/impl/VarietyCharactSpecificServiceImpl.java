package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResource;
import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResourceAssambler;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import cu.uci.cegel.onei.sigipipc.repository.VarietyCharactSpecificRepository;
import cu.uci.cegel.onei.sigipipc.services.CatchmentService;
import cu.uci.cegel.onei.sigipipc.services.VarietyCharactSpecificService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarietyCharactSpecificServiceImpl implements VarietyCharactSpecificService {

    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    VarietyEstablishmentService varietyEstablishmentService;

    @Autowired
    CaracteristicaEspecificacionResourceAssambler caracteristicaEspecificacionResourceAssambler;
    @Autowired
    CatchmentService catchmentService;

    @Override
    public List<VarietyCharactSpecific> findByVarietyCharactSpecificActive(Long classifierId) {
        List<VarietyCharactSpecific> varietyCharactSpecific = varietyCharactSpecificRepository.findAllByClassifierIdAndActiveTrue(classifierId);
        return varietyCharactSpecific;
    }

    @Override
    public List<CaracteristicaEspecificacionResource> caracteristicasSpecsByVarEstabId(Long id) {
        return caracteristicaEspecificacionResourceAssambler.toResource(catchmentService.findById(id).getDVarCaracEspec());
    }

    @Override
    public List<CaracteristicaEspecificacionResource> caracteristicasSpecsByCatchmentId(Long id) {
        return caracteristicaEspecificacionResourceAssambler.toResource(varietyEstablishmentService.getById(id).getVarietyCharactSpecifics());
    }
}
