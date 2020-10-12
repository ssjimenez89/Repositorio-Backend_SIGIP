package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.model.RegionInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.persistence.QCargo;
import cu.uci.cegel.onei.sigipipc.persistence.QRegion;
import cu.uci.cegel.onei.sigipipc.persistence.Region;
import cu.uci.cegel.onei.sigipipc.repository.RegionRepository;
import cu.uci.cegel.onei.sigipipc.resolvers.region.RegionFactory;
import cu.uci.cegel.onei.sigipipc.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    RegionFactory regionFactory;

    @Override
    public List<Region> regiones() {
        return (List<Region>) regionRepository.findAll(Sort.by("descripcion"));
    }

    @Override
    public List<Region> regionesPaging(int page, int size) {
        if(size != -1){
            Pageable pageable = PageRequest.of(page, size, Sort.by("descripcion"));
            Page <Region> regionPage = regionRepository.findAll(pageable);
            return regionPage.getContent();
        } else{
            return regiones();
        }

    }

    @Override
    public Region regionById(Long id) {
        return regionRepository.findById(id).get();
    }

    @Override
    public Long totalRegiones() {
        return regionRepository.count();
    }

    @Override
    public List<Region> regionByDescripcion(String descripcion, int page, int size) {
        QRegion qRegion = QRegion.region;
        BooleanExpression predicate = qRegion.descripcion.toLowerCase().contains(descripcion.toLowerCase());

        if (size != -1) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("descripcion"));
            Page<Region> regionPage = regionRepository.findAll(predicate, pageable);
            List<Region> regiones = regionPage.getContent();
            return regiones;
        } else {
            List<Region> regiones = (List<Region>) regionRepository.findAll(predicate, Sort.by("descripcion"));
            return regiones;
        }
    }

    @Override
    public Integer totalRegionByDescripciones(String descripcion) {
        QRegion qRegion = QRegion.region;
        BooleanExpression predicate = qRegion.descripcion.toLowerCase().contains(descripcion.toLowerCase());
        List<Region> regiones = (List<Region>) regionRepository.findAll(predicate);
        return regiones.size();
    }

    @Override
    public Region registrarRegion(RegionInput regionInput) {
        if(regionInput.getActivo() == null){
            regionInput.setActivo(true);
        }
        return regionRepository.save(regionFactory.convertirRegion(regionInput));
    }

    @Override
    public Region actualizarRegion(RegionInput regionInput) {
        Region region = regionById(regionInput.getId());
        if(regionInput.getActivo() == null){
            region.setDescripcion(regionInput.getDescripcion());
        }else{
        region.setDescripcion(regionInput.getDescripcion());
        region.setActivo(regionInput.getActivo());
        }
        return regionRepository.save(region);
    }
}
