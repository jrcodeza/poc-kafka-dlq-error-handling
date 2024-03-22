package sk.jrcodeza.poc.kafka.err.dlq.exception;

import lombok.Getter;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

@Getter
public class NotRetryableException extends RuntimeException {

    private final MyEvent myEvent;

    public NotRetryableException(MyEvent myEvent) {
        super("Not retryable exception for event [%s]".formatted(myEvent.payload()));
        this.myEvent = myEvent;
    }
}
