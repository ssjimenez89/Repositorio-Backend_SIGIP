package cu.uci.cegel.onei.sigipipc.services;


import cu.uci.cegel.onei.sigipipc.persistence.State;

import java.util.List;


public interface StateService {

    List<State> allStates();

    State stateByDescripcion(String descripcion);

    State stateById(long id);

    State addState(State estad);

    State editState(State estad);

}
