package cu.uci.cegel.base.integration.messaging.annotations;

import cu.uci.cegel.base.integration.messaging.config.KafkaNotifierConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableAspectJAutoProxy
@EnableKafka
@Import({KafkaNotifierConfiguration.class})
public @interface EnableKafkaLogger {
}
