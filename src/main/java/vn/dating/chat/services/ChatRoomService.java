package vn.dating.chat.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ChatRoomService {
    private final Map<String, Set<String>> chatRoomUsersMap = new HashMap<>();

    public void addUserToChatRoom(String chatRoomId, String sessionId) {
        Set<String> sessionIds = chatRoomUsersMap.get(chatRoomId);
        if (sessionIds == null) {
            sessionIds = new HashSet<>();
            chatRoomUsersMap.put(chatRoomId, sessionIds);
        }
        sessionIds.add(sessionId);
    }

    public void removeUserFromChatRoom(String chatRoomId, String sessionId) {
        Set<String> sessionIds = chatRoomUsersMap.get(chatRoomId);
        if (sessionIds != null) {
            sessionIds.remove(sessionId);
            if (sessionIds.isEmpty()) {
                chatRoomUsersMap.remove(chatRoomId);
            }
        }
    }

    public Set<String> getUsersInChatRoom(String chatRoomId) {
        Set<String> sessionIds = chatRoomUsersMap.get(chatRoomId);
        return sessionIds != null ? sessionIds : new HashSet<>();
    }
}
