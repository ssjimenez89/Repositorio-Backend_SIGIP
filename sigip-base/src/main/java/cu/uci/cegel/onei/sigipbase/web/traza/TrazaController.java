package cu.uci.cegel.onei.sigipbase.web.traza;

import cu.uci.cegel.onei.sigipbase.domain.trazas.Traza;
import cu.uci.cegel.onei.sigipbase.domain.trazas.TrazaService;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TipoOperacion;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.TiposTraza;
import cu.uci.cegel.onei.sigipbase.infrastructure.traza.notifier.Notifier;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaForm;
import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaResource;
import cu.uci.cegel.onei.sigipbase.web.traza.dto.TrazaResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class TrazaController {

    private static final String ENTITY_URI = "/traza";
    private static final String ENTITY_NAME = "traza";

    private final @NonNull
    TrazaService trazaService;
    private final @NonNull
    TrazaResourceAssembler trazaResourceAssembler;
    private final @NonNull
    PagedResourcesAssembler pagedResourcesAssembler;
    @Autowired
    private Notifier notifier;


    @SuppressWarnings("unchecked")
    @PostMapping(path = ENTITY_URI + "/list", consumes = "application/json")
    public ResponseEntity<PagedResources<Traza>> obtenerTrazas(Pageable pageable, @RequestBody TrazaForm trazaForm) {
        try {
            Page<Traza> trazaPage = trazaService.listarTrazas(trazaForm, pageable);
            if (trazaForm.getFechaI() != null || trazaForm.getFechaF() != null || trazaForm.getUsuario() != null || trazaForm.getCodEntidad() != null || trazaForm.getTipoOperacion() != null) {
                if (trazaPage.getTotalElements() != 0) {
                    return ResponseEntity.ok()
                            .body(pagedResourcesAssembler.toResource(trazaPage, trazaResourceAssembler));
                } else
                    return ResponseEntity.ok()
                            .headers(HeaderUtil.infoAlert("La búsqueda no arrojó resultados"))
                            .body(pagedResourcesAssembler.toResource(trazaPage, trazaResourceAssembler));
            } else
                return ResponseEntity.ok()
                        .body(pagedResourcesAssembler.toResource(trazaPage, trazaResourceAssembler));

        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo las " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<TrazaResource> obtenerTraza(@PathVariable String id) {
        try {
            Optional<Traza> traza = trazaService.obtenerTraza(id);
            notifier.publish(TiposTraza.FUNCIONAL, "Obteniendo los datos de la traza " + traza.get().getId(), TipoOperacion.GENERIC_OBTENER);
            return traza.map(traza1 -> ResponseEntity.ok().body(trazaResourceAssembler.toResource(traza1)))
                    .orElseGet(() -> ResponseEntity.badRequest()
                            .headers(HeaderUtil.errorAlert("la " + ENTITY_NAME + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("La " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }
}
