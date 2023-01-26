package vn.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User  save(User user) { return  userRepository.save(user);}
    public Optional<User> findById(Long id) {return  userRepository.findById(id);}
    public Optional<User> findByUserName(String name){ return  userRepository.findByUsername(name);};
}
