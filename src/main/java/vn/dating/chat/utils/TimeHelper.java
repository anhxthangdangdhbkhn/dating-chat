package vn.dating.chat.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeHelper {
    public static Date getCurrentDateSystemDefault(){
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

    public static Instant strToInstant(String time){
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        ZoneId japanZoneId = ZoneId.of(ZoneId.systemDefault().getId());
        Instant instant = localDateTime.atZone(japanZoneId).toInstant();
        return  instant;
    }

    public static Instant milliToInstant(Long time){
        Instant instant = Instant.ofEpochMilli(time);
        instant.atZone(ZoneId.systemDefault());
        return  instant;
    }
}
