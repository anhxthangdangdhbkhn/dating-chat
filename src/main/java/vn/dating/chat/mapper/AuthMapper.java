package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import vn.dating.chat.dto.auth.AuthDto;
import vn.dating.chat.dto.messages.api.ResultMessage;
import vn.dating.chat.model.Token;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AuthMapper {
    public static AuthDto userToAuth(Token token){
        ModelMapper modelMapper = new ModelMapper();
        AuthDto authDto = modelMapper.map(token, AuthDto.class);
        return  authDto;
    }
//    public static List<AuthDto> toGetListAccess(List<Token> tokens){
//
//        List<AuthDto> authDtos = new ArrayList<>();
//        tokens.forEach(token -> {
//            authDtos.add(userToAuth(token));
//        });
//        return  authDtos;
//    }

    public static List<AuthDto> toGetListAccess(List<Token> tokens){
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<AuthDto>>() {}.getType();
        return modelMapper.map(tokens,listType);
    }
}
