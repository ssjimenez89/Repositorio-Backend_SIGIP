package cu.uci.cegel.onei.sigipipc.kernel.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRealationFound extends RuntimeException implements GraphQLError {
    private Map<String, Object> extensions = new HashMap<>();

    public EntityRealationFound(String entityName, HttpStatus status) {
        super("La entidad " + entityName + " aún está relacionada");
        extensions.put("entityRealtion", entityName);
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
}
