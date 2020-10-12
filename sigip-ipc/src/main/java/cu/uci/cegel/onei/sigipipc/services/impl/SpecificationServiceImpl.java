package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.repository.CharacteristicRepository;
import cu.uci.cegel.onei.sigipipc.repository.SpecificationRepository;
import cu.uci.cegel.onei.sigipipc.services.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    SpecificationRepository specificationRepository;
    @Autowired
    CharacteristicRepository characteristicRepository;

    @Override
    public List<Specification> findAll() {
       List<Specification>   specificationList = (List<Specification>) specificationRepository.findAll();
        return specificationList;
    }

    @Override
    public Specification add(String description, Long characteristicId) {
        Optional<Characteristic> characteristic = characteristicRepository.findById(characteristicId);
        return specificationRepository.save(Specification.builder()
                .description(description)
                .active(true)
                .characteristic(characteristic.get())
                .build());
    }

    @Override
    public Specification edit(Long id, String description, boolean activo, Characteristic characteristic) {
        Optional<Specification> specification = specificationRepository.findById(id);

        if (specification.isPresent()) {
            Specification save = Specification.builder()
                    .active(activo)
                    .description(description)
                    .id(id)
                    .characteristic(characteristic)
                    .build();
            return specificationRepository.save(save);
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Specification> specification = specificationRepository.findById(id);

        if (specification.isPresent()) {
            specificationRepository.delete(specification.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteSpecification(List<Long> idSpecifications){
        idSpecifications.stream()
                .map(it -> delete(it))
                .collect(Collectors.toList());
        return true;
    }

    @Override
    public Specification specificationByDescription(String description) {
        return specificationRepository.findByDescriptionEquals(description);
    }

    public List<Specification> specificationListByCharacteristic(Long characteristicId){
        List<Specification> specificationList = specificationRepository.findAllByActiveTrueAndCharacteristic_Id(characteristicId);
        return specificationList;
    }

    public List<Specification> specificationListActiveDeUM(){
        List<Specification> specificationList = specificationRepository.findAllByActiveTrueAndCharacteristic_Description("Unidad de medida");
        return specificationList;
    }

    public List<Specification> specificationListActiveDeCantidad(){
        List<Specification> specificationList = specificationRepository.findAllByActiveTrueAndCharacteristic_Description("Cantidad");
        return specificationList;
    }
}
