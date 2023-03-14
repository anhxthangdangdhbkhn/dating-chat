package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupMessage;
import vn.dating.chat.dto.messages.api.ResultMessage;
import vn.dating.chat.dto.messages.socket.MessagePrivateDto;
import vn.dating.chat.dto.messages.socket.MessagePrivateGroupOutputDto;
import vn.dating.chat.model.*;
import vn.dating.chat.repositories.GroupMemberRepository;
import vn.dating.chat.repositories.GroupRepository;
import vn.dating.chat.repositories.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.security.Principal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MessageService {
    @Autowired
    private GroupRepository groupRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserReceiveService userReceiveService;

    @Autowired
    private EntityManager entityManager;





    public void sendPrivateToUser( MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getRecipientId(),"/topic/private-messages", newMessage);
    }

    public void sendPrivateMember (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-member", newMessage);
    }

    public void sendPrivateNotification (MessagePrivateDto newMessage) {
        messagingTemplate.convertAndSendToUser(newMessage.getSenderId(),"/topic/private-notification", newMessage);
    }

    public List<User> getUsersInGroup(Long groupId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT gm.user FROM GroupMember gm " +
                        "WHERE gm.group.id = :groupId",
                User.class);
        query.setParameter("groupId", groupId);
        List<User> result = query.getResultList();
        return result;
    }

    public List<String> getAllUserOfGroup(long groupId){

        log.info("groupId:" +groupId );
        List<GroupMember> groupMemberList = groupMemberRepository.findByGroupId(groupId);
        log.info("Member group size: ",groupMemberList.size());
        List<String> listUsers = new ArrayList<>();
        groupMemberList.forEach(m->{
            listUsers.add(m.getUser().getEmail());
        });

        return  listUsers;
    }

//    public void saveMessageWithReceivers( Message message, List<UserReceive> receivers) {
//        try {
//            entityManager.getTransaction().begin();
//
//            // Save the message
//            entityManager.createNativeQuery("INSERT INTO message (content, delete, group_id, sender_id) VALUES (?, ?, ?, ?)")
//                    .setParameter(1, message.getContent())
//                    .setParameter(2, message.isDelete())
//                    .setParameter(3, message.getGroup().getId())
//                    .setParameter(4, message.getSender().getId())
//                    .executeUpdate();
//
//            // Get the ID of the new message
//            Long messageId = ((BigInteger) entityManager.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult()).longValue();
//
//            // Save the receivers
//            for (UserReceive receiver : receivers) {
//                entityManager.createNativeQuery("INSERT INTO user_receive (delete, read_at, receive_chat_id, receive_user_id) VALUES (?, ?, ?, ?)")
//                        .setParameter(1, receiver.isDelete())
//                        .setParameter(2, receiver.getReadAt())
//                        .setParameter(3, messageId)
//                        .setParameter(4, receiver.getUserReceive().getId())
//                        .executeUpdate();
//            }
//
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw e;
//        }
//    }

    Long saveMessage(Message message) throws SQLException {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("INSERT INTO message (content, delete, group_id, sender_id) VALUES (?, ?, ?, ?)")
                .setParameter(1, message.getContent())
                .setParameter(2, message.isDelete())
                .setParameter(3, message.getGroup().getId())
                .setParameter(4, message.getSender().getId());

        int affectedRows = query.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating message failed, no rows affected.");
        }

        Long messageId = ((BigInteger) query.getSingleResult()).longValue();
        entityManager.getTransaction().commit();
        return messageId;
    }

    Long saveMessage(String content, Long groupId, Long userId) {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("INSERT INTO message (content, delete, group_id, sender_id) VALUES (?, ?, ?, ?)")
                    .setParameter(1, content)
                    .setParameter(2, false)
                    .setParameter(3, groupId)
                    .setParameter(4, userId);

            int affectedRows = query.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating message failed, no rows affected.");
            }

            Long messageId = ((BigInteger) query.getSingleResult()).longValue();
            entityManager.getTransaction().commit();
            return messageId;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
//            throw new RuntimeException("Failed to save message: " + e.getMessage());
        }
    }

    List<Long> getListUserIdOfGroup(Long groupId){
        List userIds = entityManager.createNativeQuery(
                        "SELECT u.id " +
                                "FROM user u " +
                                "INNER JOIN group_member gm ON u.id = gm.user_id " +
                                "WHERE gm.group_id = ?", Long.class)
                .setParameter(1, groupId)
                .getResultList();
        return userIds;
    }

    List<String> getListUserEmailOfGroup(Long groupId){
        List userIds = entityManager.createNativeQuery("SELECT u.email FROM User u JOIN u.members m WHERE m.group.id = :groupId", String.class)
                .setParameter(1, groupId)
                .getResultList();
        return userIds;
    }

    public ResultGroupMessage findMessageByGroupId(Long groupId, int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Message> messagePage =  messageRepository.findByGroupId(groupId,pageable);

        ResultGroupMessage resultGroupMessage = new ResultGroupMessage();
        resultGroupMessage.setGroupId(groupId);
        messagePage.getContent().forEach(msg->{
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setId(msg.getId());
            resultMessage.setContent(msg.getContent());
            resultMessage.setSenderEmail(msg.getSender().getEmail());
            resultMessage.setSenderName(msg.getSender().getUsername());


            resultGroupMessage.addMessage(resultMessage);

        });

        return resultGroupMessage;
    }

    @Transactional
    public boolean saveMessageWithReceivers(String content,Long groupId, Long userId, List<Long> receiverIds) {
        boolean result = false;
        try {
            Long messageId = saveMessage(content,groupId,userId);
            if(messageId ==null) return false;
            // Save the UserReceive records for each receiver
            for (Long receiverId : receiverIds) {
                Query userReceiveQuery = entityManager.createNativeQuery("INSERT INTO user_receive (read_at, delete, receive_chat_id, receive_user_id) VALUES (?, ?, ?, ?)")
                        .setParameter(1, null)
                        .setParameter(2, false)
                        .setParameter(3, messageId)
                        .setParameter(4, receiverId);
                userReceiveQuery.executeUpdate();
            }

            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return result;
    }

    @Transactional
    public boolean saveMessageWithReceivers(Message message, List<Long> receiverIds) {
        boolean result = false;
        try {
            Long messageId = saveMessage(message);
            // Save the UserReceive records for each receiver
            for (Long receiverId : receiverIds) {
                Query userReceiveQuery = entityManager.createNativeQuery("INSERT INTO user_receive (read_at, delete, receive_chat_id, receive_user_id) VALUES (?, ?, ?, ?)")
                        .setParameter(1, null)
                        .setParameter(2, false)
                        .setParameter(3, messageId)
                        .setParameter(4, receiverId);
                userReceiveQuery.executeUpdate();
            }

            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return result;
    }



    public void sendMessageToGroup(MessagePrivateGroupOutputDto messagePrivateGroupOutputDto, Principal principal) {

//        List<String> listUsers = getAllUserOfGroup(messagePrivateGroupOutputDto.getGroupId());

        log.info(messagePrivateGroupOutputDto.toString());

        List<User> userList = getUsersInGroup(messagePrivateGroupOutputDto.getGroupId());
        Group currentGroup = groupRepository.findById(messagePrivateGroupOutputDto.getGroupId()).orElse(null);


        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getEmail().contains(principal.getName())){
                userList.remove(userList.get(i));
                break;
            }
        }

        Message message = new Message();
        message.setContent(message.getContent());
        message.setDelete(false);
        message.setSender(userService.getUserByEmail(principal.getName()));
        message.setContent(messagePrivateGroupOutputDto.getContent());
        message.setGroup(currentGroup);
        message = messageRepository.save(message);

        for(int index =0;index < userList.size();index++){
            UserReceive userReceive = new UserReceive();
            userReceive.setDelete(false);
            userReceive.setCreatedAt(Instant.now());
            userReceive.setUserReceive(userList.get(index));
            userReceive.setReceiveChat(message);
            userReceiveService.save(userReceive);
        }




        for(int i=0;i<userList.size();i++){
            log.info("sent to user " +userList.get(i).getEmail());
            messagingTemplate.convertAndSendToUser(userList.get(i).getEmail(),"/topic/group-private-messages", messagePrivateGroupOutputDto);
        }
    }

    public void sendMessageCreatedGroup(ResultGroupDto resultGroupDto, Principal principal) {

//        List<String> listUsers = getAllUserOfGroup(messagePrivateGroupOutputDto.getGroupId());
//        if(listUsers.contains(principal.getName())){
//            listUsers.remove(principal.getName());
//        }
//
//        for(int i=0;i<listUsers.size();i++){
//            log.info("sent to user " +listUsers.get(i));
//            messagingTemplate.convertAndSendToUser(listUsers.get(i),"/topic/create-group-messages", resultGroupDto);
//        }
    }
}
