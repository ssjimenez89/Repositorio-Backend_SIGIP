package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.model.DPAResource;
import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import cu.uci.cegel.onei.sigipipc.persistence.QDPA;
import cu.uci.cegel.onei.sigipipc.persistence.Region;
import cu.uci.cegel.onei.sigipipc.repository.DPARepository;
import cu.uci.cegel.onei.sigipipc.repository.RegionRepository;
import cu.uci.cegel.onei.sigipipc.services.DPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DPAServiceImpl implements DPAService {
    @Autowired
    private DPARepository dpaRepository;
    @Autowired
    private RegionRepository regionRepository;



    @Override
    public List<DPA> getAllActive() {
        return dpaRepository.findAllByActiveTrueOrderByDescriptionAsc();
    }

    @Override
    public List<DPA> findAll() {
        return (List<DPA>) dpaRepository.findAll();
    }

    @Override
    public List<DPAResource> findAllDPAResource() {
        List<DPA> dpaList = getAllActive();
        return dpaList.stream().map(dpa -> {
            return DPAResource.builder()
                    .id(dpa.getId())
                    .description(dpa.getCode() + "-" + dpa.getDescription())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<DPA> findAllActives() {
        return dpaRepository.findByActiveIsTrueOrderByDescriptionAsc();
    }


    @Override
    public List<DPA> dpasPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<DPA> page = dpaRepository.findAll(pageable);
            List<DPA> dpas = page.getContent();
            return dpas;
        } else {
            return findAll();
        }
    }

    @Override
    public List<DPA> allChildrensByIdParent(long idParent) {
        DPA dpaPadre = dpaRepository.findById(idParent).get();
        List<DPA> allChildrens = dpaRepository.findAllByParentEquals(dpaPadre);
        return allChildrens;
    }

    @Override
    public int totalDpa() {
        List<DPA> dpas = (List<DPA>) dpaRepository.findAll();
        return dpas.size();
    }

    @Override
    public DPA dpaById(Long id) {
        DPA dpa = dpaRepository.findById(id).get();
        return dpa;
    }

    @Override
    public List<DPA> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<DPA> page = dpaRepository.findAll(getPredicate(description), pageable);
            List<DPA> dpas = page.getContent();
            return dpas;
        } else {
            List<DPA> dpas = (List<DPA>) dpaRepository.findAll(getPredicate(description), Sort.by("description"));
            return dpas;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<DPA> dpas = (List<DPA>) dpaRepository.findAll(getPredicate(description), Sort.by("description"));
        return dpas.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QDPA.dPA.description.toLowerCase().contains(description.toLowerCase());
    }


    @Override
    public DPA addDPA(String code, String description, boolean active, Long regionId, Long IdParent) {

        DPA dpa = new DPA(code, description, active);
        if(regionId != null){
            Region region =  regionRepository.findById(regionId).get();
            dpa.setRegion(region);
        }
        if (!Objects.isNull(IdParent)) {
            DPA padre = dpaRepository.findById(IdParent).get();
            dpa.setParent(padre);
        }
        return dpaRepository.save(dpa);
    }


    @Override
    public DPA editDPA(long id, String code, String description, boolean active, Long regionId, Long IdParent) {

        DPA dpa = dpaRepository.findById(id).get();
        dpa.setCode(code);
        dpa.setDescription(description);
        dpa.setActive(active);
        if(regionId != null){
            Region region =  regionRepository.findById(regionId).get();
            dpa.setRegion(region);
        }

        if (!Objects.isNull(IdParent)) {
            DPA padre = dpaRepository.findById(IdParent).get();
            dpa.setParent(padre);
        }
        return dpaRepository.save(dpa);
    }
}
