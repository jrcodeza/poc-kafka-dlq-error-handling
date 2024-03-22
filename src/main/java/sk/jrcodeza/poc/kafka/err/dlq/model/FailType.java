package sk.jrcodeza.poc.kafka.err.dlq.model;

public enum FailType {

    NONE,
    FAIL_PARTIALLY,
    FAIL_PERMANENTLY,
    FAIL_NOT_RETRIABLE

}
