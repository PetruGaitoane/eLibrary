package com.example.demo.sevice;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserEntityService {


    private UserEntityRepository userEntityRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder){
        this.userEntityRepository = userEntityRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserEntity getUserById(Long userId){
        return userEntityRepository.findById(userId).orElse(null);
    }

    public Boolean addUser (UserEntity user){
        List<UserEntity> userList = userEntityRepository.findAll();
        if(userList.stream().anyMatch(adduser -> adduser.getUserName().equals(user.getUserName()) &&
                adduser.getUserEmail().equals(user.getUserEmail()))) {
            return false;
        }

//        UserEntity newUser = new UserEntity();
//        user.setUserFirstName(newUser.getUserFirstName());
//        user.setUserLastName(newUser.getUserLastName());
//        user.setUserName(newUser.getUserName());
//        user.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
//        user.setUserContactNumber(newUser.getUserContactNumber());
//        user.setUserEmail(newUser.getUserEmail());

        userEntityRepository.saveAndFlush(user);
        return true;
    }


    public List<UserEntity> getUserWithFilters(Map<String, Object> params) {
        if(params.size() == 0){
            return userEntityRepository.findAll();
        } else if(params.containsKey("firstName")){
            return userEntityRepository.findUserByUserName((String) params.get("firstName"));
        } else if(params.containsKey("lastName")){
            return userEntityRepository.findUserByUserName((String) params.get("lastName"));
        } else if(params.containsKey("name")){
            return userEntityRepository.findUserByUserName((String) params.get("name"));
        } else if(params.containsKey("ContactNumber")){
            return userEntityRepository.findUserByUserContactNumber(Integer.parseInt((String) params.get("ContactNumber")));
        } else if(params.containsKey("email")){
            return userEntityRepository.findUserByUserEmailIgnoreCase((String) params.get("email"));
        } else {
            return null;
        }
    }

    public UserEntity updateUser (Long userId, UserEntity updatedUser) {
        return userEntityRepository.findById(userId).map( user -> {
            user.setUserFirstName(updatedUser.getUserFirstName());
            user.setUserLastName(updatedUser.getUserLastName());
            user.setUserName(updatedUser.getUserName());
            user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));
            user.setUserContactNumber(updatedUser.getUserContactNumber());
            user.setUserEmail(updatedUser.getUserEmail());
            return userEntityRepository.saveAndFlush(user);
        }).orElse(null);

    }

    public Boolean  deleteUser (Long userId) {

        List<UserEntity> userList = userEntityRepository.findAll();
        // !!!!change to find by id;
        if(userList.stream().anyMatch(user -> user.getUserId().equals(userId))){
            userEntityRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserEntity assignUserToRole(Long userId, Long roleId) {
        Set<Role> roles = null;
        UserEntity user = userEntityRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        return userEntityRepository.save(user);

    }

//    public void inItRolesAndUsers(){
//        Role adminRole = new Role();
//        adminRole.setRoleName("ADMIN");
//        adminRole.setRoleDescription("The library admin role");
//        roleRepository.save(adminRole);
//
//        Role userRole = new Role();
//        userRole.setRoleName("USER");
//        userRole.setRoleDescription("Regular user(browse, buy) in the library");
//        roleRepository.save(userRole);
//
//        UserEntity adminUser = new UserEntity();
//        adminUser.setUserFirstName("defaultAdminF");
//        adminUser.setUserLastName("defaultAdminL");
//        adminUser.setUserName("defaultAdmin");
//        adminUser.setUserPassword("admin");
//        adminUser.setUserContactNumber(1111111111);
//        adminUser.setUserEmail("defaultAdmin@admin.com");
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminRoles.add(userRole);
//        adminUser.setRoles(adminRoles);
//        userEntityRepository.save(adminUser);
//
//        UserEntity user = new UserEntity();
//        user.setUserFirstName("defaultUserF");
//        user.setUserLastName("defaultUserL");
//        user.setUserName("defaultUser");
//        user.setUserPassword("user");
//        user.setUserContactNumber(1222222222);
//        user.setUserEmail("defaultUser@user.com");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRoles(userRoles);
//        userEntityRepository.save(user);
//    }

}
