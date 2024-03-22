package sk.jrcodeza.poc.kafka.err.dlq.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sk.jrcodeza.poc.kafka.err.dlq.exception.NotRetryableException;
import sk.jrcodeza.poc.kafka.err.dlq.exception.PermanentException;
import sk.jrcodeza.poc.kafka.err.dlq.exception.RetryableException;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MyEventHandler implements Consumer<Message<MyEvent>> {

    public static final int MAX_TIMES_PARTIAL_FAIL = 3;
    public static final Map<String, AtomicInteger> retryTimes = new ConcurrentHashMap<>();

    @Override
    public void accept(Message<MyEvent> message) {
        var event = message.getPayload();
        log.info("-------------------------------------------------------[{}]--------------------------------------", event.payload());
        log.info("Received event payload=[{}] failType=[{}]", event.payload(), event.failType());
        log.info(
                "Headers: [ {} ]",
                message.getHeaders()
                        .entrySet()
                        .stream()
                        .map(it-> "[%s]:[%s]".formatted(it.getKey(), it.getValue()))
                        .collect(Collectors.joining(", "))
        );
        switch (event.failType()) {
            case NONE -> {
                log.info("Event [{}] processed successfully!".formatted(event.payload()));
            }
            case FAIL_NOT_RETRIABLE -> throw new NotRetryableException(event);
            case FAIL_PARTIALLY -> {
                var timesRetried = retryTimes.compute(
                        event.payload(),
                        (s, atomicInteger) -> {
                            if (atomicInteger == null) {
                                return new AtomicInteger(0);
                            }
                            atomicInteger.incrementAndGet();
                            return atomicInteger;
                        });
                if (timesRetried.get() > MAX_TIMES_PARTIAL_FAIL) {
                    log.info("Event [{}] processed successfully!".formatted(event.payload()));
                } else {
                    log.warn("Simulating partial failure on event [{}]", event.payload());
                    throw new RetryableException(event);
                }
            }
            case FAIL_PERMANENTLY -> throw new PermanentException(event);
        }
        log.info("-------------------------------------------------------------------------------------------------");
    }

}

