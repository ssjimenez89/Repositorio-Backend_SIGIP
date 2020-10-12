package cu.uci.cegel.onei.sigipipc.services;


import cu.uci.cegel.onei.sigipipc.persistence.Typology;

import java.util.List;

public interface TypologyService {
    List<Typology> findAll();

    Typology add(String description, Long categoryID, Boolean active, Boolean imputed);

    Typology edit(Long id, String description, Long categoryId, Boolean active, Boolean imputed);

    List<Typology> findAllPaging(int page, int size);

    int totalTypology();

    Typology typologyById(Long id);

    List<Typology> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);
}
