package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Incidence;

import java.util.List;

public interface IncidenceService {
    List<Incidence> findAll();

    Incidence add(String description, String acronym, Boolean active);

    Incidence incidenceByAcronym(String acronym);

    Incidence edit(Long id, String description, String acronym, Boolean active);

    List<Incidence> findAllPaging(int page, int size);

    int totalIncidence();

    Incidence incidenceById(Long id);

    List<Incidence> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);
}

