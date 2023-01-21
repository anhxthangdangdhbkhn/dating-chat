package vn.dating.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.dating.chat.model.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {
}
