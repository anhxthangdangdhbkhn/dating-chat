package vn.dating.chat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.dating.chat.model.Message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    Message save(Message message);

    Page<Message> findByGroupId(Long groupId, Pageable pageable);

    @Query(value = "SELECT m.* " +
            "FROM message m " +
            "WHERE m.group_id = :groupId AND m.created_at > :createdAtAfter " +
            "ORDER BY m.created_at DESC ",nativeQuery = true)
    Page<Message> findMessagesByGroupIdAndCreatedAtAfterOrderByCreatedAtDesc(
            @Param("groupId") Long groupId,
            @Param("createdAtAfter") Instant afterTime,
            Pageable pageable);

    @Query(value = "SELECT m.* FROM message m WHERE m.group_id = :groupId ORDER BY m.created_at DESC",nativeQuery = true)
    List<Message> findLastTenMessagesByGroupId(@Param("groupId") Long groupId, Pageable pageable);

}
