package cu.uci.cegel.onei.sigipbase.web.dpa;

import cu.uci.cegel.onei.sigipbase.domain.dpa.Dpa;
import cu.uci.cegel.onei.sigipbase.domain.dpa.DpaService;
import cu.uci.cegel.onei.sigipbase.infrastructure.util.UtilValidacion;
import cu.uci.cegel.onei.sigipbase.kernel.CONSTANTS;
import cu.uci.cegel.onei.sigipbase.web.dpa.dto.DpaResource;
import cu.uci.cegel.onei.sigipbase.web.dpa.dto.DpaResourceAssembler;
import cu.uci.cegel.security.utils.HeaderUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = CONSTANTS.ENTITY_API, produces = "application/json")
public class DpaController {

    private final @NonNull
    DpaService dpaService;
    private final @NonNull
    DpaResourceAssembler dpaResourceAssembler;

    private static final String ENTITY_URI = "/dpa";
    private static final String ENTITY_NAME = "dpa";


    @GetMapping(path = ENTITY_URI + "/all")
    public ResponseEntity<List<DpaResource>> obtenerTodosDpa() {
        try {
            List<Dpa> dpas = dpaService.listarTodosDpa();
            return ResponseEntity.ok().body(dpaResourceAssembler.toResources(dpas));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("Error obteniendo los " + ENTITY_NAME + "s en el servidor.")).build();
        }
    }

    @GetMapping(path = ENTITY_URI + "/{id}")
    public ResponseEntity<DpaResource> obtenerDpa(@PathVariable Long id) {
        try {
            if (!UtilValidacion.isAValidIdentification(id)) {
                return ResponseEntity.badRequest()
                        .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no puede tener un id con valor " + id + ".")).build();
            }
            return dpaService.obtenerDpaNew(id).map(dpa -> ResponseEntity.ok()
                    .body(dpaResourceAssembler.toResource(dpa)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .headers(HeaderUtil.errorAlert("El " + ENTITY_NAME + " no se encuentra en el servidor.")).build());
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.badRequestAlert("El " + ENTITY_NAME + " no se puede obtener.")).build();
        }
    }
}
