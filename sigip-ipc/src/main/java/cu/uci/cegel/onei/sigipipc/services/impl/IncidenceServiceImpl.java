package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Incidence;
import cu.uci.cegel.onei.sigipipc.persistence.QIncidence;
import cu.uci.cegel.onei.sigipipc.repository.IncidenceRepository;
import cu.uci.cegel.onei.sigipipc.services.IncidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenceServiceImpl implements IncidenceService {
    @Autowired
    IncidenceRepository incidenceRepository;

    @Override
    public List<Incidence> findAll() {
        return (List<Incidence>) incidenceRepository.findAllByActiveTrue();
    }

    @Override
    public Incidence add(String description, String acronym, Boolean active) {
        return incidenceRepository.save(new Incidence(description, acronym, active));
    }

    @Override
    public Incidence incidenceByAcronym(String acronym) {
        return incidenceRepository.findByAcronymEquals(acronym);
    }

    @Override
    public Incidence edit(Long id, String description, String acronym, Boolean active) {
        Incidence incidence = incidenceRepository.findById(id).get();
        incidence.setDescription(description);
        incidence.setAcronym(acronym);
        incidence.setActive(active);
        return incidenceRepository.save(incidence);
    }

    @Override
    public List<Incidence> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Incidence> page = incidenceRepository.findAll(pageable);
            List<Incidence> incidences = page.getContent();
            return incidences;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalIncidence() {
        List<Incidence> incidences = (List<Incidence>) incidenceRepository.findAll();
        return incidences.size();
    }

    @Override
    public Incidence incidenceById(Long id) {
        Incidence incidence = incidenceRepository.findById(id).get();
        return incidence;
    }

    @Override
    public List<Incidence> findByDescriptionContains(String description, int paging, int size) {
        QIncidence qIncidence = QIncidence.incidence;
        BooleanExpression predicate = qIncidence.description.toLowerCase().contains(description.toLowerCase());
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Incidence> page = incidenceRepository.findAll(predicate, pageable);
            List<Incidence> incidences = page.getContent();
            return incidences;
        } else {
            List<Incidence> incidences = (List<Incidence>) incidenceRepository.findAll(predicate, Sort.by("description"));
            return incidences;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        QIncidence qIncidence = QIncidence.incidence;
        BooleanExpression predicate = qIncidence.description.toLowerCase().contains(description.toLowerCase());
        List<Incidence> incidences = (List<Incidence>) incidenceRepository.findAll(predicate, Sort.by("description"));
        return incidences.size();
    }
}
