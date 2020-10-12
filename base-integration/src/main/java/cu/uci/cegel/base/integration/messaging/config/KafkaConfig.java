package cu.uci.cegel.base.integration.messaging.config;

import lombok.Getter;

@Getter
public enum KafkaConfig {

//    IP_SERVER("localhost:9092"),
    IP_SERVER("10.58.12.5:9092"),
//    IP_SERVER("192.168.150.55:9092"),
    TOPIC_TRAZA("trace");

    String descripcion;

    KafkaConfig(String descripcion) {
        this.descripcion = descripcion;
    }
}
