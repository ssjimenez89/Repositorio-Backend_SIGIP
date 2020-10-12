package cu.uci.cegel.onei.sigipbase.web.reportes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ReportesController.ENTITY_API, produces = "application/json")
public class ReportesController {

    protected static final String ENTITY_API = "/api/v1.0";
    private static final String ENTITY_URI = "/reportes";
    @Value("${jasperserver.url}")
    String url;
    @Value("${jasperserver.username}")
    String username;
    @Value("${jasperserver.password}")
    String password;

    /**
     * Login on JasperServer
     */
    @GetMapping(value = ENTITY_URI + "/jasperserver/login")
    public ResponseEntity<Map<String, String>> loginJasperReportsServer() {

        Map<String, String> map = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String fullUrl = url + "?j_username=" + username + "&j_password=" + password;
        HttpHeaders headers;
        try {
            headers = restTemplate.headForHeaders(fullUrl);
        } catch (HttpClientErrorException e) {
            headers = e.getResponseHeaders();
        }
        String jSessionId = headers.getFirst(HttpHeaders.SET_COOKIE);
        if (jSessionId == null) {
            map.put("error", "unable to get session id from jasperserver");
            return ResponseEntity.badRequest().body(map);
        }
        map.put("cookie", jSessionId);
        return ResponseEntity.ok(map);
    }
}
