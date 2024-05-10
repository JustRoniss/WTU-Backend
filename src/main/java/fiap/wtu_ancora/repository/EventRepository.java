package fiap.wtu_ancora.repository;

import fiap.wtu_ancora.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
