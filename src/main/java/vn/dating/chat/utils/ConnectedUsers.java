package vn.dating.chat.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectedUsers {

    private static final ConcurrentHashMap<String, Set<String>> connectedUsers = new ConcurrentHashMap<>();

    public static void addUser(String userId, String session) {

        if(connectedUsers.containsKey(userId)){
            connectedUsers.putIfAbsent(userId,new HashSet<>()).add(session);
        }else {
            Set<String> item = new HashSet<>();
            item.add(session);
            connectedUsers.put(userId,item);
        }
    }

    public static String getOnlineUsers() {
        return connectedUsers.toString();
    }

    public static void removeUser(String userId,String session) {
        connectedUsers.get(userId).remove(session);
    }



}

