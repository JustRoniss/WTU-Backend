package fiap.wtu_ancora.repository;

import fiap.wtu_ancora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String username);
}
