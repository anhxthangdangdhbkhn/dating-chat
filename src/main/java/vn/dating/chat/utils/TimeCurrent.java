package vn.dating.chat.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeCurrent {
    public static Date getDateSystemDefault(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant japanTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date currentTime  = Date.from(japanTime);
        return currentTime;
    }
    public static Instant getInstantSystemDefault(){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        Instant japanTime = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
//        Date currentTime  = Date.from(japanTime);
        Instant now = Instant.now();
        now.atZone(ZoneId.systemDefault());
//        Date currentTime  = Date.from(now);
//        System.out.println(currentTime);
//        System.out.println(currentTime.getYear() +"-" +currentTime.getMonth() + "-" + currentTime.getDate() +" "
//                + currentTime.getHours()+":" + currentTime.getMinutes() +":"
//                +currentTime.getSeconds());
        return  now;
    }
}
