package vn.dating.chat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.dating.chat.model.UserReceive;

public interface UserReceiveRepository extends JpaRepository<UserReceive,Long> {

    Page<UserReceive> findByGroupChatId(Long groupId, Pageable pageable);
}
