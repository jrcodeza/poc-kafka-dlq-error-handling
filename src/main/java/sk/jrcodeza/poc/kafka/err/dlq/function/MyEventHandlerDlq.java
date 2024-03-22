package sk.jrcodeza.poc.kafka.err.dlq.function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyEventHandlerDlq implements Consumer<Message<MyEvent>> {

    private final MyEventHandler myEventHandler;

    @Override
    public void accept(Message<MyEvent> myEventMessage) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>DLQ<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Processing event from DLQ");
        myEventHandler.accept(myEventMessage);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>___<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
