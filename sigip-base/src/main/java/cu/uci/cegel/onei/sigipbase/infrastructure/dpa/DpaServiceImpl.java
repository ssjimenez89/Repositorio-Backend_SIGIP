package cu.uci.cegel.onei.sigipbase.infrastructure.dpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipbase.domain.dpa.Dpa;
import cu.uci.cegel.onei.sigipbase.domain.dpa.DpaRepository;
import cu.uci.cegel.onei.sigipbase.domain.dpa.DpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DpaServiceImpl implements DpaService {

    @Autowired
    DpaRepository dpaRepository;

    @Override
    public List<Dpa> listarTodosDpa() {
        return dpaRepository.findByActivoIsTrueOrderByDescripcionAsc();
    }

    @Override
    public Dpa obtenerDpa(Long id) {
        return dpaRepository.findByIdEquals(id);
    }

    @Override
    public Optional<Dpa> obtenerDpaNew(Long id) {
        return dpaRepository.findById(id);
    }
}
