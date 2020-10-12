package cu.uci.cegel.onei.sigipbase.web.rol;

import cu.uci.cegel.onei.sigipbase.domain.rol.Rol;
import cu.uci.cegel.onei.sigipbase.domain.rol.RolService;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TipoOperacion;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TiposTraza;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.notifier.Notifier;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilValidacion;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolDTO;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolForm;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolResource;
import cu.uci.cegel.onei.sigipbase.web.rol.dto.RolResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class RolController {

    private final @NonNull
    RolService rolService;
    private final @NonNull
    RolResourceAssembler rolResourceAssembler;
    private final @NonNull
    PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private Notifier notifier;

    private static final String ENTITY_URI = "/rol";
    private static final String ENTITY_NAME = "rol";

    @PostMapping(path = ENTITY_URI)
    public ResponseEntity<RolResource> registrarRol(@RequestBody RolDTO rolDTO) {
        try {
            if (rolService.existeRol(rolDTO.getDescription().toString()) != null) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El rol " + rolDTO.getDescription() + " ya existe.")).build();
            } else {
                Rol rol = rolService.registrarRol(rolDTO);
                notifier.publish(TiposTraza.FUNCIONAL, "Se ha creado el " + ENTITY_NAME + " " + rol.getDescription(), TipoOperacion.GENERIC_REGISTRAR);
                return ResponseEntity.created(new URI(CONSTANTS.ENTITY_API + ENTITY_URI + "/" + rol.getId()))
                        .headers(HeaderUtil.infoAlert("Se ha creado el rol " + rolDTO.getDescription() + " correctamente."))
                        .body(rolResourceAssembler.toResource(rol));
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede registrar.")).build();
        }
    }

    @PutMapping(path = ENTITY_URI + "/{id}", consumes = "application/json")
    public ResponseEntity<RolResource> actualizarRol(@PathVariable Long id, @RequestBody RolDTO rolDTO) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            if (rolService.obtenerRol(id).isPresent()) {
                Rol rol = rolService.actualizarRol(id, rolDTO);
                notifier.publish(TiposTraza.FUNCIONAL, "Se ha modificado el " + ENTITY_NAME + " " + rol.getDescription(), TipoOperacion.GENERIC_MODIFICAR);
                return ResponseEntity.ok()
                        .headers(HeaderUtil.infoAlert("Se ha modificado el rol " + rolDTO.getDescription() + "."))
                        .body(rolResourceAssembler.toResource(rol));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede actualizar.")).build();
        }
    }

    @SuppressWarnings("unchecked")
    @PostMapping(path = ENTITY_URI + "/list", consumes = "application/json")
    public ResponseEntity<PagedResources<Rol>> obtenerRoles(Pageable pageable, @RequestBody RolForm rolForm) {
        try {
            Page<Rol> rolPage = rolService.listarRoles(rolForm, pageable);
            if (rolForm.getRol() != null) {
                if (rolPage.getTotalElements() != 0)
                    return ResponseEntity.ok()
                            .body(pagedResourcesAssembler.toResource(rolPage, rolResourceAssembler));
                else
                    return ResponseEntity.ok()
                            .headers(HeaderUtil.infoAlert("La búsqueda no arrojó resultados."))
                            .body(pagedResourcesAssembler.toResource(rolPage, rolResourceAssembler));
            } else
                return ResponseEntity.ok()
                        .body(pagedResourcesAssembler.toResource(rolPage, rolResourceAssembler));

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "es en el servidor.")).build();
        }
    }

    @RequestMapping(path = ENTITY_URI + "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RolResource> obtenerRol(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            Optional<Rol> rol = rolService.obtenerRol(id);
            notifier.publish(TiposTraza.FUNCIONAL, "Obteniendo los datos del " + ENTITY_NAME + " " + rol.get().getDescription(), TipoOperacion.GENERIC_OBTENER);
            return rol.map(rol1 -> ResponseEntity.ok().body(rolResourceAssembler.toResource(rol1)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + rol.get().getRol() + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }

    @RequestMapping(value = ENTITY_URI + "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<RolResource>> obtenerRoles(boolean todos) {
        try {
            return ResponseEntity.ok().body(rolResourceAssembler.toResources(rolService.obtenerRoles(todos)));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los" + ENTITY_NAME + "es en el servidor.")).build();
        }
    }

    @RequestMapping(value = ENTITY_URI + "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> eliminarRol(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            Optional<Rol> rol = rolService.obtenerRol(id);
            if (!rol.isPresent()) {
                return ResponseEntity.ok()
                        .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " con id " + rol.get().getId() + " no se encuentra en el servidor.")).build();
            } else if (rolService.eliminarRol(rol))
                return ResponseEntity.ok()
                        .headers(HeaderUtil.infoAlert("Se ha eliminado el " + ENTITY_NAME + " " + rol.get().getDescription() + " correctamente.")).build();
            else {
                return ResponseEntity.ok()
                        .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se puede eliminar.")).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }
}
