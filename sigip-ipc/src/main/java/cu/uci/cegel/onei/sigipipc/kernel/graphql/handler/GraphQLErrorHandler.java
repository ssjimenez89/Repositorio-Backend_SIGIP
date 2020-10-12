package cu.uci.cegel.onei.sigipipc.kernel.graphql.handler;

import cu.uci.cegel.onei.sigipipc.kernel.errors.EntityRealationFound;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GraphQLErrorHandler implements graphql.servlet.GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream().map(this::getNested).collect(Collectors.toList());
    }

    protected boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }

    private GraphQLError getNested(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            if (exceptionError.getException() instanceof GraphQLError) {
                return (GraphQLError) exceptionError.getException();
            }
            if (exceptionError.getException() instanceof DataIntegrityViolationException) {
                String msg = exceptionError.getException().getCause().getCause().getLocalizedMessage();
                return new EntityRealationFound(getTableName(msg), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return error;
    }

    private String getTableName(String localizedMessage) {
        Pattern p = Pattern.compile("([a-z]+)(?=[»])");
        Matcher m = p.matcher(localizedMessage);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}

