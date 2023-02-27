package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.User;

import java.util.List;

public class GroupMapper {

    public static ResultGroupDto toGetGroup(Group group){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupDto resultGroupDto = modelMapper.map(group, ResultGroupDto.class);
        return  resultGroupDto;
    }
    public static ResultGroupDto toGetGroupOfUser(Group group,User current, List<User> members){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupDto resultGroupDto = modelMapper.map(group, ResultGroupDto.class);
        if(members.size()==2){
            if(members.get(1).getEmail().contains(current.getEmail())){
                resultGroupDto.setAvatar(members.get(0).getAvatar());
                resultGroupDto.setName(members.get(0).getUsername());
            }else {
                resultGroupDto.setAvatar(members.get(1).getAvatar());
                resultGroupDto.setName(members.get(1).getUsername());
            }

        }else resultGroupDto.setAvatar("https://via.placeholder.com/50x50");
        resultGroupDto.setMembers(UserMapper.toGetListUsers(members));
        return  resultGroupDto;
    }
}
