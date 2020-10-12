package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.persistence.OneiIndex;
import cu.uci.cegel.onei.sigipipc.repository.OneiIndexRepository;
import cu.uci.cegel.onei.sigipipc.services.OneiIndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OneiIndexServiceImpl implements OneiIndexService {

    private final OneiIndexRepository oneiIndexRepository;

    public OneiIndexServiceImpl(OneiIndexRepository oneiIndexRepository) {
        this.oneiIndexRepository = oneiIndexRepository;
    }

    @Override
    public List<OneiIndex> getAll() {
        return (List<OneiIndex>) oneiIndexRepository.findAll();
    }

    @Override
    public OneiIndex addOneiIndex(String description) {
        OneiIndex oneiIndex = new OneiIndex(description);
        return oneiIndexRepository.save(oneiIndex);
    }
}
