package vn.dating.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.model.Role;
import vn.dating.chat.model.RoleName;


import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    Optional<Role> findById(RoleName roleName);


    @Query(value = "DELETE FROM user_roles WHERE user_id = ?1 AND role_id =?2 ",nativeQuery = true)
    @Modifying
    @Transactional
    void removeRoleUser(long userId,long roleId);
}
