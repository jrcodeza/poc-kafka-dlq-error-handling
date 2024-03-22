package sk.jrcodeza.poc.kafka.err.dlq.exception;

import lombok.Getter;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

@Getter
public class PermanentException extends RuntimeException {

    private final MyEvent myEvent;

    public PermanentException(MyEvent myEvent) {
        super("Permanent exception for event [%s]".formatted(myEvent.payload()));
        this.myEvent = myEvent;
    }

}
