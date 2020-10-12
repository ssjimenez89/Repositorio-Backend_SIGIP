package cu.uci.cegel.onei.sigipbase.infrastructure.permiso;

import cu.uci.cegel.onei.sigipbase.domain.permiso.*;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoDTO;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;
    private final PermisoFactory permisoFactory;

    @Autowired
    public PermisoServiceImpl(PermisoRepository permisoRepository, PermisoFactory permisoFactory) {
        this.permisoRepository = permisoRepository;
        this.permisoFactory = permisoFactory;
    }

    @Override
    public Permiso registrarPermiso(PermisoDTO permisoDTO) {
        return permisoRepository.save(permisoFactory.convertir(permisoDTO));
    }

    @Override
    public Permiso actualizarPermiso(Long idpermiso, PermisoDTO permisoDTO) {
        return permisoRepository.save(permisoFactory.convertir(obtenerPermiso(idpermiso), permisoDTO));
    }

    @Override
    public Optional<Permiso> obtenerPermiso(Long idpermiso) {
        return permisoRepository.findById(idpermiso);
    }

    @Override
    public Boolean desactivarPermiso(Long idpermiso) {
        Optional<Permiso> optionalPermiso = obtenerPermiso(idpermiso);
        if (optionalPermiso.isPresent()) {
            Permiso permiso = optionalPermiso.get();
            permiso.setActivo(false);
            permisoRepository.save(permiso);
            return true;
        }
        return false;
    }

    @Override
    public List<Permiso> listarPermisos() {
        return permisoRepository.findByActivoIsTrueOrderByDescription();
    }

    public List<Permiso> buscarPermisosQueContengan(List<Long> Permiso) {
        return permisoRepository.obtenerPermisosIn(Permiso);
    }

    @Override
    public Page<Permiso> listarPermisosPage(PermisoForm permisoForm, Pageable pageable) {
        return permisoRepository.findAll(PermisoSpecs.listarPermisos(permisoForm), pageable);
    }

    @Override
    public List<Permiso> obtenerPermisos(List<Long> permisos) {
        return permisoRepository.obtenerPermisosIn(permisos);
    }
}
