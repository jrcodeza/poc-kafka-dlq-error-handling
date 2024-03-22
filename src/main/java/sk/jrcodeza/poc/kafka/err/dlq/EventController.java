package sk.jrcodeza.poc.kafka.err.dlq;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sk.jrcodeza.poc.kafka.err.dlq.function.EventProducer;
import sk.jrcodeza.poc.kafka.err.dlq.model.MyEvent;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventProducer eventProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody @Validated MyEvent myEvent) {
        eventProducer.sendEvent(myEvent);
    }

}
