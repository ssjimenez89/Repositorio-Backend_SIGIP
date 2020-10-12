package cu.uci.cegel.onei.sigipipc.util;

import cu.uci.cegel.onei.sigipipc.model.CharacteristicSpecificationResource;
import cu.uci.cegel.onei.sigipipc.model.SpecificationResource;
import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import cu.uci.cegel.onei.sigipipc.repository.CharacteristicRepository;
import cu.uci.cegel.onei.sigipipc.repository.SpecificationRepository;
import cu.uci.cegel.onei.sigipipc.repository.VarietyCharactSpecificRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CharacteristicSpecificationResourceAssembler {
    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;

    @Autowired
    SpecificationRepository specificationRepository;


    public CharacteristicSpecificationResource toResource(Characteristic characteristic) {

        return CharacteristicSpecificationResource.builder()
                    .id(characteristic.getId())
                    .active(characteristic.getActive())
                    .description(characteristic.getDescription())
                    .totalespecifications(specificationRepository.countSpecificationsByCharacteristic_Id(characteristic.getId()))
                    .notAllowElimination(findNotAllowElimination(characteristic))
                    .build();
    }

    //Para cuando se va a Editar o Visualizar las caracterisiticas, cargar las especificaciones.
    public CharacteristicSpecificationResource toResourceConEspecificaciones(Characteristic characteristic) {

        List<Specification> especifications = specificationRepository.findByCharacteristicEquals(characteristic);
        return CharacteristicSpecificationResource.builder()
                .id(characteristic.getId())
                .active(characteristic.getActive())
                .description(characteristic.getDescription())
                .especifications(toResourcesSpecifications(especifications))
                .totalespecifications(especifications.size())
                .notAllowElimination(findNotAllowElimination(characteristic))
                .build();

    }


    private boolean findNotAllowElimination(Characteristic characteristic) {
         List<VarietyCharactSpecific> byCharacteristic = varietyCharactSpecificRepository.findBySpecification_Characteristic(characteristic);
       if (!byCharacteristic.isEmpty()) {
            List<Classifier> collect = byCharacteristic
                    .stream()
                    .map(VarietyCharactSpecific::getClassifier)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return collect.size() == 0;
        }
        return true;
    }

    private List<Specification> findSpecifications(Characteristic characteristic) {
        return specificationRepository.findByCharacteristicEquals(characteristic);
    }

    private SpecificationResource toResourceSpecification(Specification specification) {
        return SpecificationResource.builder()
                .id(specification.getId())
                .description(specification.getDescription())
                .active(specification.getActive())
                .notAllowElimination(findNotAllowEliminationSpecification(specification))
                .build();
    }

    private boolean findNotAllowEliminationSpecification(Specification specification) {
        List<VarietyCharactSpecific> bySpecificacion = varietyCharactSpecificRepository.findBySpecification(specification);
        if (!bySpecificacion.isEmpty()) {
            List<Classifier> collect = bySpecificacion
                    .stream()
                    .map(VarietyCharactSpecific::getClassifier)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return collect.size() == 0;
        }
        return true;
    }

    private List<SpecificationResource> toResourcesSpecifications(List<Specification> specifications) {
        return specifications.stream().map(this::toResourceSpecification).collect(Collectors.toList());
    }

    public List<CharacteristicSpecificationResource> toResources(List<Characteristic> characteristics) {
        return characteristics.stream().map(this::toResource).collect(Collectors.toList());
    }
}
