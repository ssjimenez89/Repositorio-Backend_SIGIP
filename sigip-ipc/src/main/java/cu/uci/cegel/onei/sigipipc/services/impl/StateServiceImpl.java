package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.persistence.State;
import cu.uci.cegel.onei.sigipipc.repository.StateRepository;
import cu.uci.cegel.onei.sigipipc.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;


    @Override
    public List<State> allStates() {
        return (List<State>) stateRepository.findAll();
    }

    @Override
    public State stateByDescripcion(String descripcion) {
        return stateRepository.findByDescripcionEquals(descripcion);
    }

    @Override
    public State stateById(long idEstado) {
        return stateRepository.findById(idEstado).get();
    }

    @Override
    public State addState(State estad) {
        return stateRepository.save(estad);
    }

    @Override
    public State editState(State estado) {
        return stateRepository.save(estado);
    }
}
