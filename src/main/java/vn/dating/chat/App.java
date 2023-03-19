package vn.dating.chat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        String time = "2023-03-18 14:43:57.100215";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);


        System.out.println(instant);
        System.out.println( instant.toEpochMilli());
    }
}
