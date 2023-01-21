package vn.dating.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.dating.chat.model.Message;

@EnableJpaRepositories
public interface MessageRepository extends JpaRepository<Message,Long> {
}
