package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.Event;
import fiap.wtu_ancora.model.Unit;
import fiap.wtu_ancora.model.User;
import fiap.wtu_ancora.model.dto.EventDTO;
import fiap.wtu_ancora.repository.EventRepository;
import fiap.wtu_ancora.repository.UnitRepository;
import fiap.wtu_ancora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get-all")
    public List<Event> getAllEvents() {return eventRepository.findAll();}

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDto) {
        Set<Unit> units = new HashSet<>();
        for(Long unitId : eventDto.getUnitIds()) {
            Unit unit = unitRepository.findById(unitId).orElse(null);
            if(unit != null) {
                units.add(unit);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setUnits(units);
        event.setIframe(eventDto.getIframe());

        Set<User> users = new HashSet<>();
        for (String email : eventDto.getUsersEmail()) {
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                users.add(user);
            }
        }
        event.setUsers(users);

        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable Long id, @RequestBody Event eventDatails){
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            event.setTitle(eventDatails.getTitle());
            event.setDescription(eventDatails.getDescription());
            event.setStartDate(eventDatails.getStartDate());
            event.setEndDate(eventDatails.getEndDate());
            event.setIframe(eventDatails.getIframe());
            final Event updateEvent = eventRepository.save(event);
            return ResponseEntity.ok(updateEvent);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable Long id){
        try{
            eventRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
