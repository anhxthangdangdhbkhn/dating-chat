package vn.dating.chat.dto.messages;

import lombok.*;
import vn.dating.chat.dto.auth.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
@Data
public class ChatInfo {
    private static final AtomicInteger counter = new AtomicInteger();

    public ChatInfo() {
        this.id = this.counter.getAndIncrement();
    }

    public ChatInfo(List<String> users,String name, String time) {
        this.id = this.counter.getAndIncrement();
        this.member.add(users.get(0));
        this.member.add(users.get(1));
        this.time = time;
        this.name = name;
    }

    public ChatInfo(List<String> member, String admin, String name, String time) {
        this.id = this.counter.getAndIncrement();
        this.name = name;
        this.time = time;
        this.admin = admin;

        member.forEach(m->{
            this.member.add(m);
        });
    }

    private int id;
    private String name;
    private String time;
    private String admin;
    private List<String> member = new ArrayList<>();

    private List<UserDto>  memberInfo = new ArrayList<>();
}
