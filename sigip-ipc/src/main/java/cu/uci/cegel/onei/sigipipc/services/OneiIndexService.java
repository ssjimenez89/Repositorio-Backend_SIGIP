package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.OneiIndex;

import java.util.List;

public interface OneiIndexService {
    List<OneiIndex> getAll();

    OneiIndex addOneiIndex(String description);
}
