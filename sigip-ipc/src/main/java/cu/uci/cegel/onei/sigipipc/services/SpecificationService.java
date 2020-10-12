package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Characteristic;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;

import java.util.List;

public interface SpecificationService {
    List<Specification> findAll();

    Specification add(String description, Long characteristicId);

    Specification edit(Long id, String description, boolean activo, Characteristic characteristic);

    Boolean delete(Long id);

    Boolean deleteSpecification(List<Long> idSpecifications);

    Specification specificationByDescription(String description);

    List<Specification> specificationListByCharacteristic(Long characteristicId);

    List<Specification> specificationListActiveDeUM();

    List<Specification> specificationListActiveDeCantidad();



}
