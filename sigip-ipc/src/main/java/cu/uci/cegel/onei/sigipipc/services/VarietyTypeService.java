package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.VarietyType;

import java.util.List;

public interface VarietyTypeService {

    List<VarietyType> findAll();

    List<VarietyType> varietyTypesPaging(int page, int size);

    VarietyType varietyTypeById(Long id);

    int totalVarietyTypes();

    List<VarietyType> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    VarietyType addVarietyType(String description, Boolean active);

    VarietyType editVarietyType(Long id, String description, Boolean active);
}
