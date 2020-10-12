package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.WeighingType;

import java.util.List;

public interface WeighingTypeService {

    List<WeighingType> findAll();

    List<WeighingType> weighingTypesPaging(int page, int size);

    WeighingType weighingTypeById(Long id);

    int totalWeighingTypes();

    List<WeighingType> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    WeighingType addWeighingType(String description, Boolean active);

    WeighingType editWeighingType(Long id, String description, Boolean active);
}
