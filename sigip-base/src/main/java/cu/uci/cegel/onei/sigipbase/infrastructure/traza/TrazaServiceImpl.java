package cu.uci.cegel.onei.sigipbase.infrastructure.traza;

import cu.uci.cegel.base.integration.messaging.events.SystemEvent;
import cu.uci.cegel.onei.sigipbase.domain.trazas.*;
import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrazaServiceImpl implements TrazaService {

    @Autowired
    private MongoOperations mongoOperations;
    private final TrazaFactory trazaFactory;
    private final TrazaRepository trazaRepository;

    @Autowired
    public TrazaServiceImpl(TrazaFactory trazaFactory, TrazaRepository trazaRepository) {
        this.trazaFactory = trazaFactory;
        this.trazaRepository = trazaRepository;
    }

    @Override
    public Traza salvarTraza(Traza traza) {
        return trazaRepository.save(traza);
    }

    @Override
    public Optional<Traza> obtenerTraza(String id) {
        return trazaRepository.findById(id);
    }

    @Override
    public Page<Traza> listarTrazas(TrazaForm trazaForm, Pageable pageable) {
        return trazaRepository.findAll(TrazaSpec.listar(trazaForm), pageable);
    }

    @KafkaListener(topics = "sigip")
    public void processTrace(SystemEvent systemEvent) {
        this.salvarTraza(trazaFactory.createForm(systemEvent));
    }
}
