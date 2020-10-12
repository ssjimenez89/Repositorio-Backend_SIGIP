package cu.uci.cegel.onei.sigipipc.kernel.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityNotFound extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();
    private HttpStatus status;

    public EntityNotFound(String entityName, Long invalidEntityId, HttpStatus status) {
        super("La entidad " + entityName + " con id " + invalidEntityId + " no existe");
        extensions.put("invalidEntityId", invalidEntityId);
        extensions.put("statusCode", status.value());
    }

    public EntityNotFound(String entityName, HttpStatus status) {
        super("La entidad " + entityName + " no existe");
        extensions.put("invalidEntity", "entityName");
        extensions.put("statusCode", status.value());
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
