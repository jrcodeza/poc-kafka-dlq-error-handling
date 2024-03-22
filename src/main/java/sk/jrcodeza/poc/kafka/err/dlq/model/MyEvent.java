package sk.jrcodeza.poc.kafka.err.dlq.model;

public record MyEvent(String payload, FailType failType) {
}
