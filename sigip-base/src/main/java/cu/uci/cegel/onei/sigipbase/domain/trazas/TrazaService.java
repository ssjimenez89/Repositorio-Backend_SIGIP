package cu.uci.cegel.onei.sigipbase.domain.trazas;

import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TrazaService {

    Traza salvarTraza(Traza traza);

    Optional<Traza> obtenerTraza(String id);

    Page<Traza> listarTrazas(TrazaForm trazaForm, Pageable pageable);
}
