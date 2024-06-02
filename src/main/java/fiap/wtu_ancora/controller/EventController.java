package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.Event;
import fiap.wtu_ancora.model.Unit;
import fiap.wtu_ancora.model.User;
import fiap.wtu_ancora.dto.EventDTO;
import fiap.wtu_ancora.dto.UnitDTO;
import fiap.wtu_ancora.dto.UserDTO;
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
import java.util.stream.Collectors;

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
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> {
            Set<UnitDTO> units = event.getUnits().stream().map(unit -> new UnitDTO(unit.getId())).collect(Collectors.toSet());
            Set<UserDTO> users = event.getUsers().stream().map(user -> new UserDTO(user.getEmail())).collect(Collectors.toSet());
            EventDTO eventDTO = new EventDTO();
            eventDTO.setTitle(event.getTitle());
            eventDTO.setDescription(event.getDescription());
            eventDTO.setStartDate(event.getStartDate());
            eventDTO.setEndDate(event.getEndDate());
            eventDTO.setIframe(event.getIframe());
            eventDTO.setUnits(units);
            eventDTO.setUsers(users);
            return eventDTO;
        }).collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDto) {
        Set<Unit> units = new HashSet<>();
        for(UnitDTO unitDTO : eventDto.getUnits()) {
            Unit unit = unitRepository.findById(unitDTO.getId()).orElse(null);
            if(unit != null) {
                units.add(unit);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        Set<User> users = new HashSet<>();
        for(UserDTO userDTO : eventDto.getUsers()) {
            User user = userRepository.findUserByEmail(userDTO.getEmail());
            if (user != null) {
                users.add(user);
            }
        }

        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setUnits(units);
        event.setUsers(users);
        event.setIframe(eventDto.getIframe());

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
