package vn.dating.chat.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.dating.chat.mapper.AuthMapper;
import vn.dating.chat.mapper.MessageMapper;
import vn.dating.chat.model.Message;
import vn.dating.chat.model.Token;
import vn.dating.chat.repositories.TokenRepository;
import vn.dating.chat.utils.PagedResponse;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private TokenRepository tokenRepository;

    public PagedResponse findTokensByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Token> tokens =  tokenRepository.findByUserTokenId(userId,pageable);

        if(tokens.getNumberOfElements()==0){
            return new PagedResponse<>(Collections.emptyList(), tokens.getNumber(), tokens.getSize(),
                    tokens.getTotalElements(), tokens.getTotalPages(), tokens.isLast());
        }


        return new PagedResponse<>(AuthMapper.toGetListAccess(tokens.stream().toList()), tokens.getNumber(), tokens.getSize(), tokens.getTotalElements(),
                tokens.getTotalPages(), tokens.isLast());
    }
}
