package vn.dating.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.dating.chat.exceptions.AppException;
import vn.dating.chat.exceptions.ResourceNotFoundException;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.Role;
import vn.dating.chat.model.RoleName;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.RoleRepository;
import vn.dating.chat.repositories.UserRepository;
import vn.dating.chat.utils.NotificationEmail;
import vn.dating.chat.utils.PagedResponse;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  MailService mailService;


    @Autowired
    private EntityManager entityManager;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findUsersByEmails(List<String> emails) {
        List<User> users = userRepository.findByEmails(emails);
        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> {
            throw new ResourceNotFoundException("user","id",id);});
    }

    public User findById(Long id) {
        return  userRepository.findById(id).orElse(null);
    }

    Long findUserIdByEmail(String email){
        Long userId;
        try {
            userId = entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return userId;
        } catch (NoResultException ex) {
           return null;
        }
    }


    public boolean existsUserById(Long id){
        List user = userRepository.findUserById(id);
        if(user.size()==1) return true;
        else  return  false;
    }



    public User save(User user){return  userRepository.save(user);};

    public Optional<User> findByEmail(String email){return userRepository.findByEmail(email);}
    public boolean existsByEmail(String email) {return  userRepository.existsByEmail(email);}
    public User saveAndFlush(User user){return  userRepository.saveAndFlush(user);}

    public void removeRoleUser(long userId,long roleId){roleRepository.removeRoleUser( userId, roleId);}



    public Optional<User> findByCreateToken(String token){return userRepository.findByCreateToken(token);}

    public Optional<User>findByEmailAndPassword(String email,String password){return userRepository.findByEmailAndPassword(email,password);};

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public PagedResponse getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);

        if(users.getNumberOfElements()==0){
            return new PagedResponse<>(Collections.emptyList(), users.getNumber(), users.getSize(),
                    users.getTotalElements(), users.getTotalPages(), users.isLast());
        }

        return new PagedResponse<>(UserMapper.toGetListUsers(users.stream().toList()), users.getNumber(), users.getSize(), users.getTotalElements(),
                users.getTotalPages(), users.isLast());
    }

    public User createUserSendEmailActiveAccount(User user){

        String verificationToken  = UUID.randomUUID().toString();

        String passNew = passwordEncoder.encode(user.getPassword());

        user.setPassword(passNew);
        user.setCreateToken(verificationToken);
        user.setCreateExpiry(Instant.now().plusMillis(10 *60 *1000));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        mailService.sendMail(new NotificationEmail(
                "Please Activate your Account",
                user.getEmail(),
                "Thank you for signing up to heyShop, " +
                        "please click on the below url to activate your account : " +
                        "http://localhost:1999/api/v1/auth/verify/" + verificationToken));

        user = userRepository.save(user);
        return user;
    }

    public boolean userVerify(User user){
        if(user.getCreateExpiry().isBefore(Instant.now())){
            return false;
        }
        user.setCreateToken("");
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }

    public List<User> getUsersInGroup(Long groupId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT gm.user FROM GroupMember gm " +
                        "WHERE gm.group.id = :groupId",
                User.class);
        query.setParameter("groupId", groupId);
        List<User> result = query.getResultList();
        return result;
    }


}

