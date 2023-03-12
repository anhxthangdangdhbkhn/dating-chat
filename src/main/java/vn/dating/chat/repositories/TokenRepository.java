package vn.dating.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.model.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    List<Token> findByAccessTokenOrRefreshToken(String accessToken, String refreshToken);

    @Transactional
    void deleteByAccessTokenOrRefreshToken(String accessToken, String refreshToken);


    Optional<Token> findByRefreshToken(String accesstoken);
}
