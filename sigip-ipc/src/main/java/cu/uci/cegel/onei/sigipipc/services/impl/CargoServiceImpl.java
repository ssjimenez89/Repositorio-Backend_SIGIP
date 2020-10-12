package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.model.CargoInput;
import cu.uci.cegel.onei.sigipipc.persistence.Cargo;
import cu.uci.cegel.onei.sigipipc.persistence.QCargo;
import cu.uci.cegel.onei.sigipipc.repository.CargoRepository;
import cu.uci.cegel.onei.sigipipc.resolvers.cargo.CargoFactory;
import cu.uci.cegel.onei.sigipipc.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    CargoFactory cargoFactory;

    //QUERYS!!!!!!

    @Override
    public List<Cargo> listarCargos() {
        return (List<Cargo>) cargoRepository.findAll(Sort.by("descripcion"));
    }

    @Override
    public List<Cargo> listarCargosPaging(int page, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("descripcion"));
            Page<Cargo> cargoPage = cargoRepository.findAll(pageable);
            return cargoPage.getContent();
        } else {
            return listarCargos();
        }

    }

    @Override
    public Cargo obtenerCargo(Long id) {
        return cargoRepository.findById(id).get();
    }


    @Override
    public Long totalCargos() {
        return cargoRepository.count();
    }

    @Override
    public List<Cargo> cargosPorDescripcion(String descripcion, int page, int size) {
        QCargo qCargo = QCargo.cargo;
        BooleanExpression predicate = qCargo.descripcion.toLowerCase().contains(descripcion.toLowerCase());

        if (size != -1) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("descripcion"));
            Page<Cargo> cargoPage = cargoRepository.findAll(predicate, pageable);
            List<Cargo> cargos = cargoPage.getContent();
            return cargos;
        } else {
            List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll(predicate, Sort.by("descripcion"));
            return cargos;
        }
    }

    @Override
    public Integer totalCargosPorDescripcion(String descripcion) {
        QCargo qCargo = QCargo.cargo;
        BooleanExpression predicate = qCargo.descripcion.toLowerCase().contains(descripcion.toLowerCase());
        List<Cargo> cargos = (List<Cargo>) cargoRepository.findAll(predicate);
        return cargos.size();
    }

    //MUTATIONS!!!!!!

    @Override
    public Cargo registrarCargo(CargoInput cargoInput) {
        if(cargoInput.getActivo() == null){
            cargoInput.setActivo(true);
        }
        return cargoRepository.save(cargoFactory.convertirCargo(cargoInput));
    }

    @Override
    public Cargo actualizarCargo(CargoInput cargoInput) {
        Cargo cargo = obtenerCargo(cargoInput.getId());
        if(cargoInput.getActivo() == null){
            cargo.setDescripcion(cargoInput.getDescripcion());
        }else {
            cargo.setDescripcion(cargoInput.getDescripcion());
            cargo.setActivo(cargoInput.getActivo());
        }
        return cargoRepository.save(cargo);
    }
}
