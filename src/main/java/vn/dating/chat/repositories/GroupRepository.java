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

    @Query(value ="SELECT DISTINCT g.* FROM public.group g JOIN group_member m ON g.id = m.group_id WHERE m.user_id = :userId",nativeQuery = true)
    Page<Group> findDistinctByMembersUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value ="SELECT g.* "
            + "FROM public.group g "
            + "INNER JOIN message m ON m.group_id = g.id "
            + "INNER JOIN users u ON u.id = m.sender_id "
            + "WHERE u.id = :userId "
            + "GROUP BY g.id "
            + "HAVING MAX(m.created_at) IS NOT NULL "
            + "ORDER BY MAX(m.created_at) DESC",nativeQuery = true)
    Page<Group> findGroupsByUserIdOrderByLastMessage(@Param("userId") Long userId, Pageable pageable);

}

