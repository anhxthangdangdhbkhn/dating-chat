package vn.dating.chat.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.dating.chat.model.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);



//    @Query(value = "SELECT g FROM Group g WHERE :userId IN (SELECT m.sender.id FROM Message m WHERE m.group.id = g.id) ORDER BY (SELECT MAX(m.createdAt) FROM Message m WHERE m.group.id = g.id) DESC",nativeQuery = true)
//    Page<Group> findByUserIdOrderByLatestMessageCreatedAtDesc(Long userId, Pageable pageable);


//    @Query(value ="SELECT DISTINCT g FROM group g JOIN g.members m WHERE m.user.id = :userId",nativeQuery = true)
//    Page<Group> findDistinctByMembersUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value ="SELECT DISTINCT g.* FROM public.group g JOIN group_member m ON g.id = m.group_id WHERE m.user_id = :userId",nativeQuery = true)
    Page<Group> findDistinctByMembersUserId(@Param("userId") Long userId, Pageable pageable);


}

