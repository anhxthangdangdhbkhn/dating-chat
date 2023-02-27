package vn.dating.chat.utils;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder

public class ApiGroupResponse {
    private ApiGroupType type;
    private Object content;
}
