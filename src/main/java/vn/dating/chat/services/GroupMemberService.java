package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.GroupMemberType;
import vn.dating.chat.model.GroupType;
import vn.dating.chat.repositories.GroupMemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class GroupMemberService {


    @Autowired
    private  GroupMemberRepository groupMemberRepository;

    @Autowired
    private EntityManager entityManager;


    public List<GroupMember> getGroupMembersByGroupId(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    public List<GroupMember> getGroupMembersByUserId(Long userId) {
        return groupMemberRepository.findByUserId(userId);
    }

    public void saveGroupMember(GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
    }

    public void deleteGroupMemberById(Long id) {
        groupMemberRepository.deleteById(id);
    }

    public GroupMember getGroupMemberById(Long id) {
        return groupMemberRepository.findById(id).orElse(null);
    }


    public List<Long> getGroupIdForUsers(Long userId1, Long userId2) {
        String jpql = "SELECT gm1.group.id FROM GroupMember gm1 JOIN GroupMember gm2 " +
                "ON gm1.group.id = gm2.group.id " +
                "WHERE gm1.user.id = :userId1 AND gm2.user.id = :userId2";

        Query query = entityManager.createQuery(jpql)
                .setParameter("userId1", userId1)
                .setParameter("userId2", userId2);
        List<Long> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }

    public boolean existChatTwoUser(Long userId1, Long userId2) {

        String jpql = "SELECT gm.group.id FROM GroupMember gm " +
                "WHERE gm.user.id IN (:userId1, :userId2) " +
                "AND gm.group.type = :groupType " +
                "AND gm.type = :groupMemberType " +
                "GROUP BY gm.group.id " +
                "HAVING COUNT(gm.group.id) = 2";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("userId1", userId1);
        query.setParameter("userId2", userId2);
        query.setParameter("groupType", GroupType.PRIVATE);
        query.setParameter("groupMemberType", GroupMemberType.PRIVATE);

        List<Long> result = query.getResultList();

        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}

