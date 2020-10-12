package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Observation;

import java.util.List;

public interface ObservationService {

    List<Observation> findAll();

    Observation add(String description, String acronym, Boolean active);

    Observation observationByDescription(String description);

    Observation observationByAcronym(String acronym);

    Observation edit(Long id, String description, String acronym, Boolean active);

    List<Observation> findAllPaging(int page, int size);

    int totalObservation();

    Observation observationById(Long id);

    List<Observation> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    List<Observation> observationByVariedad(Long varId);

}
