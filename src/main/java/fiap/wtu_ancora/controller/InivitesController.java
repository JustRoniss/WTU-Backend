package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.Event;
import fiap.wtu_ancora.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invites")
public class InivitesController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/get-invites/{email}")
    public ResponseEntity<List<Event>> getEventsForUser(@PathVariable String email) {
        List<Event> events = eventRepository.findEventsByUserEmail(email);
        return ResponseEntity.ok(events);
    }
}
