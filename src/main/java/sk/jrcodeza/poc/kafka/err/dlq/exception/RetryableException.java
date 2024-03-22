package sk.jrcodeza.poc.kafka.err.dlq.exception;

import lombok.Getter;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

@Getter
public class RetryableException extends RuntimeException {

    private final MyEvent myEvent;

    public RetryableException(MyEvent myEvent) {
        super("Retryable exception for event [%s]".formatted(myEvent.payload()));
        this.myEvent = myEvent;
    }

}
