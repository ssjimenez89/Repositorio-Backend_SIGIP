package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.model.PlanningInput;
import cu.uci.cegel.onei.sigipipc.persistence.Establishment;
import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.repository.EstablishmentRepository;
import cu.uci.cegel.onei.sigipipc.repository.OneiIndexRepository;
import cu.uci.cegel.onei.sigipipc.repository.PlanningRepository;
import cu.uci.cegel.onei.sigipipc.services.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private OneiIndexRepository oneiIndexRepository;


    @Override
    public List<Planning> findAll() {
        List<Planning> planning = (List<Planning>) planningRepository.findAll();
        Collections.sort(planning, (n1, n2) -> n1.getId().compareTo(n2.getId()));
        return planning;
    }

    @Override
    public List<Planning> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Planning> page = planningRepository.findAll(pageable);
            List<Planning> planning = page.getContent();
            Collections.sort(planning, (n1, n2) -> n1.getId().compareTo(n2.getId()));
            return planning;
        } else {
            return findAll();
        }
    }

    @Override
    public Planning addPlaning(PlanningInput planningInput) {
        //List<Establishment> establishments =  planningInput.getEstablishmentsId().stream().map(sbt -> establishmentRepository.findById(sbt).get()).collect(Collectors.toList());
        if (Objects.isNull(planningInput.getIndexId())) {
            Planning planning = new Planning(planningInput.getWeek(), planningInput.getDay());
            return planningRepository.save(planning);
        } else {
//            OneiIndex oneiIndex = oneiIndexRepository.findById(planningInput.getIndexId()).get();
            Planning planning = new Planning(planningInput.getWeek(), planningInput.getDay());
            return planningRepository.save(planning);
        }


    }

    @Override
    public Planning editPlaning(PlanningInput planningInput) {
        Planning planning = planningRepository.findById(planningInput.getId()).get();
        List<Establishment> establishments = planningInput.getEstablishmentsId().stream().map(sbt -> establishmentRepository.findById(sbt).get()).collect(Collectors.toList());
        //planning.setEstablishments(establishments);
        planning.setWeek(planningInput.getWeek());
        planning.setDay(planningInput.getDay());
        if (!Objects.isNull(planningInput.getIndexId())) {
//            OneiIndex oneiIndex = oneiIndexRepository.findById(planningInput.getIndexId()).get();
//            planning.setIndex(oneiIndex);
        }
        return planningRepository.save(planning);
    }

    @Override
    public Planning planningById(long id) {
        Optional<Planning> temp = planningRepository.findById(id);
        return temp.get();
    }

    @Override
    public int totalPlannings() {
        return (int) planningRepository.count();
    }

    public List<Planning> planningsDayXWeek(int week1, int week2, int week3, int week4) {
        List<Planning> result = new ArrayList<>();
        List<Integer> week = new ArrayList<>();
        week.add(week1);
        week.add(week2);
        week.add(week3);
        week.add(week4);
        for (int i = 0; i < week.size(); i++) {
            List<Planning> plannings = planningRepository.findAllByWeekEqualsOrderByIdAsc(week.get(i));
            plannings.stream()
                    .map(It -> result.add(It))
                    .collect(Collectors.toList());
        }
        return result;
    }


}
