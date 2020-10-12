package cu.uci.cegel.onei.sigipbase.web.permiso;

import cu.uci.cegel.onei.sigipbase.domain.permiso.Permiso;
import cu.uci.cegel.onei.sigipbase.domain.permiso.PermisoService;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilValidacion;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoDTO;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoForm;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoResource;
import cu.uci.cegel.onei.sigipbase.web.permiso.dto.PermisoResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//import sun.plugin.javascript.navig.Array;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class PermisoController {

    private final @NonNull
    PermisoService permisoService;
    private final @NonNull
    PermisoResourceAssembler permisoResourceAssembler;
    private final @NonNull
    PagedResourcesAssembler pagedResourcesAssembler;

    private static final String ENTITY_URI = "/permiso";
    private static final String ENTITY_NAME = "permiso";

    @PostMapping(path = ENTITY_URI)
    public ResponseEntity<PermisoResource> registrarPermiso(@RequestBody PermisoDTO permisoDTO) {
        try {
            Permiso permiso = permisoService.registrarPermiso(permisoDTO);
            return ResponseEntity.created(new URI(CONSTANTS.ENTITY_API + ENTITY_URI + "/" + permiso.getId()))
                    .headers(HeaderUtil.infoAlert("Se ha creado el " + ENTITY_NAME + " correctamente."))
                    .body(permisoResourceAssembler.toResource(permiso));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede registrar.")).build();
        }
    }

    @PutMapping(path = ENTITY_URI + "/{id}", consumes = "application/json")
    public ResponseEntity<PermisoResource> actualizarPermiso(@PathVariable Long id, @RequestBody PermisoDTO permisoDTO) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            if (permisoService.obtenerPermiso(id).isPresent()) {
                Permiso permiso = permisoService.actualizarPermiso(id, permisoDTO);
                return ResponseEntity.ok()
                        .headers(HeaderUtil.infoAlert("Se ha actualizado el " + ENTITY_NAME + " correctamente."))
                        .body(permisoResourceAssembler.toResource(permiso));
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
    public ResponseEntity<PagedResources<PermisoResource>> obtenerPermisos(Pageable pageable, @RequestBody PermisoForm permisoForm) {
        try {
            Page<Permiso> permisoPage = permisoService.listarPermisosPage(permisoForm, pageable);
           if(permisoPage.getTotalElements() != 0) {
               return ResponseEntity.ok()
                       .body(pagedResourcesAssembler.toResource(permisoPage, permisoResourceAssembler));
           }else
               return ResponseEntity.ok()
                       .headers(HeaderUtil.infoAlert("La búsqueda no arrojó resultados."))
                       .body(pagedResourcesAssembler.toResource(permisoPage, permisoResourceAssembler));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<PermisoResource> obtenerPermiso(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id+ ".")).build();
            }
            return permisoService.obtenerPermiso(id).map(permiso1 -> ResponseEntity.ok().body(permisoResourceAssembler.toResource(permiso1)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }

    @GetMapping(value = ENTITY_URI + "/permisos")
    public ResponseEntity<List<PermisoResource>> obtenerPermisos() {
        try {
            return ResponseEntity.ok().body(permisoResourceAssembler.toResources(permisoService.listarPermisos()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/all")
    public ResponseEntity<List<PermisoResource>> obtenerTodosPermisos() {
        try {
            return ResponseEntity.ok().body(permisoResourceAssembler.toResources(permisoService.listarPermisos()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @PostMapping(path = ENTITY_URI + "/permisosin", consumes = "application/json")
    public ResponseEntity<List<PermisoResource>> obtenerPermisosQueContengan(@RequestBody List<Long> listadoPermisos) {
        try {
            List<Permiso> listaPermisos = permisoService.buscarPermisosQueContengan(listadoPermisos);
            return ResponseEntity.ok().body(permisoResourceAssembler.toResources(listaPermisos));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }

}
