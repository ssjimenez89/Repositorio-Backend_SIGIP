package cu.uci.cegel.onei.sigipbase.domain.dpa;

import java.util.List;
import java.util.Optional;

public interface DpaService {

    List<Dpa> listarTodosDpa();

    Dpa obtenerDpa(Long id);

    Optional<Dpa> obtenerDpaNew(Long id);
}
