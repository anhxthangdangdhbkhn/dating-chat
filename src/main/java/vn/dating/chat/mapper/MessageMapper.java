package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import vn.dating.chat.dto.messages.api.ResultMessageDto;
import vn.dating.chat.model.Message;

import java.lang.reflect.Type;
import java.util.List;

public class MessageMapper {
    public static ResultMessageDto toMessage(Message message){
        ModelMapper modelMapper = new ModelMapper();
        ResultMessageDto resultMessageDto = modelMapper.map(message, ResultMessageDto.class);
        return resultMessageDto;
    }

    public static List<ResultMessageDto>  toMessages(List<Message> messages){
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<ResultMessageDto>>() {}.getType();
        return modelMapper.map(messages,listType);
    }
}
