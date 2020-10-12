package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Observation;
import cu.uci.cegel.onei.sigipipc.persistence.QObservation;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;
import cu.uci.cegel.onei.sigipipc.repository.ObservationRepository;
import cu.uci.cegel.onei.sigipipc.services.CatchmentService;
import cu.uci.cegel.onei.sigipipc.services.ObservationService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationServiceImpl implements ObservationService {

    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    VarietyEstablishmentService varietyEstablishmentService;

    @Autowired
    CatchmentService catchmentService;

    @Override
    public List<Observation> findAll() {
        return (List<Observation>) this.observationRepository.findAll(Sort.by("description"));
    }

    @Override
    public Observation add(String description, String acronym, Boolean active) {
        Observation observation = new Observation(description, acronym, active);
        return observationRepository.save(observation);
    }

    @Override
    public Observation observationByDescription(String description) {
        return observationRepository.findByDescriptionEquals(description);
    }

    @Override
    public Observation observationByAcronym(String acronym) {
        return observationRepository.findByAcronymEquals(acronym);
    }

    @Override
    public Observation edit(Long id, String description, String acronym, Boolean active) {
        Observation observation = observationRepository.findById(id).get();
        observation.setDescription(description);
        observation.setAcronym(acronym);
        observation.setActive(active);
        return observationRepository.save(observation);
    }

    @Override
    public List<Observation> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Observation> page = observationRepository.findAll(pageable);
            List<Observation> observations = page.getContent();
            return observations;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalObservation() {
        List<Observation> observations = (List<Observation>) observationRepository.findAll();
        return observations.size();
    }

    @Override
    public Observation observationById(Long id) {
        Observation observation = observationRepository.findById(id).get();
        return observation;
    }

    @Override
    public List<Observation> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Observation> page = observationRepository.findAll(getPredicate(description), pageable);
            List<Observation> observations = page.getContent();
            return observations;
        } else {
            List<Observation> observations = (List<Observation>) observationRepository.findAll(getPredicate(description), Sort.by("description"));
            return observations;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<Observation> observations = (List<Observation>) observationRepository.findAll(getPredicate(description), Sort.by("description"));
        return observations.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QObservation.observation.description.toLowerCase().contains(description.toLowerCase());
    }

    public List<Observation> observationByVariedad(Long varId) {
        List<Observation> list = findAll();
        VarietyEstablishment var = varietyEstablishmentService.findByid(varId);
        boolean isEstatal = var.getEstablishment().getMarket().getMarket().getId() == 1;
        if (isEstatal) {
            LocalDate end = LocalDate.now();
            LocalDate start = end.minusMonths(2);

            int cant = catchmentService.cantFaltaOcacional(varId, var.getEstablishment().getId(), java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));
            if (cant >= 2) {
                list.stream().filter(e -> {
                    return e.getId() == 5;
                }).collect(Collectors.toList());
            }
        }
        return list;
    }
}
