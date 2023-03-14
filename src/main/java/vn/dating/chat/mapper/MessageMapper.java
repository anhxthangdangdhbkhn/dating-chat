package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import vn.dating.chat.dto.messages.api.ResultMessage;
import vn.dating.chat.model.Message;

import java.lang.reflect.Type;
import java.util.List;

public class MessageMapper {
    public static ResultMessage toMessage(Message message){
        ModelMapper modelMapper = new ModelMapper();
        ResultMessage resultMessage = modelMapper.map(message, ResultMessage.class);
        return  resultMessage;
    }

    public static List<ResultMessage>  toMessages(List<Message> messages){
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<ResultMessage>>() {}.getType();
        return modelMapper.map(messages,listType);
    }
}
