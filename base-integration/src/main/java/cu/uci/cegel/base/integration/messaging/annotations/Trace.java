package cu.uci.cegel.base.integration.messaging.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods to be logged through integration with Kafka.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trace {

    /**
     * @return One of the predefined topic used to send the message.
     */
    String topic();

    String traceType() default "none";

    /**
     * @return Description given when the annotated method is {@code void}
     */
    String traceDescription() default "no description";
}
