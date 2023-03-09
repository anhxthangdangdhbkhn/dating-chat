package vn.dating.chat.dto.messages.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultGroupMessage {
    private Long groupId;
    private List<ResultMessage> messages = new ArrayList<>();

    public void addMessage(ResultMessage resultMessage){
        this.messages.add(resultMessage);
    }
}
