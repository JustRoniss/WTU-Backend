package fiap.wtu_ancora.repository;

import fiap.wtu_ancora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
