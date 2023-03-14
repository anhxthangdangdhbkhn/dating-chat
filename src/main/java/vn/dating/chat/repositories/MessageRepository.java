package vn.dating.chat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.dating.chat.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    Message save(Message message);

    Page<Message> findByGroupId(Long groupId, Pageable pageable);


}
