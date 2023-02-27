package vn.dating.chat.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GroupMemberServiceTest {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void getGroupIdForUsers() {

        long userId1 = 1;
        long userId2 = 2;

        List<Long> groupId = groupMemberService.getGroupIdForUsers(userId1,userId2);
        if(groupId==null){
            System.out.println("groupId if is ull");
        }else{
            System.out.println("Group ID: " + groupId);
        }


    }
}