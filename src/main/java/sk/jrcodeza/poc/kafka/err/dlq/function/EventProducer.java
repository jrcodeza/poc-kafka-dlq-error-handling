package sk.jrcodeza.poc.kafka.err.dlq.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Supplier;

@Component
@Slf4j
public class EventProducer implements Supplier<MyEvent> {

    private final Queue<MyEvent> toSend = new ArrayDeque<>();

    @Override
    public MyEvent get() {
        var event = toSend.poll();
        if (event == null) {
            log.debug("No event in queue");
            return null;
        }
        log.info("Sending event [{}]", event.payload());
        return event;
    }

    public void sendEvent(MyEvent event) {
        toSend.add(event);
    }

}
