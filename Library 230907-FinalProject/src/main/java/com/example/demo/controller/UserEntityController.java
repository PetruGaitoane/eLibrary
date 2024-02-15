package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.helpers.CustomErrorResponse;
import com.example.demo.sevice.UserEntityService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/library") //http://localhost:8080/api/library
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;

    @GetMapping("/users/{userId}") //http://localhost:8080/api/library/user/{userId}
    public ResponseEntity<Object> getUserById(@PathVariable Long userId){
        ResponseEntity<Object> response = null;
        try{
            UserEntity user = userEntityService.getUserById(userId);
            if(user != null){
                response = new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "User id: "+ userId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/users") //http://localhost:8080/api/library/users
    public ResponseEntity<Object> getUsersWithFilter(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> response = null;
        try{
            List<UserEntity> userList = userEntityService.getUserWithFilters(params);
            if(userList != null && userList.size() > 0){
                response = new ResponseEntity<>(userEntityService.getUserWithFilters(params), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "User not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/users") //http://localhost:8080/api/library/users
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserEntity user){
        ResponseEntity<Object> response = null;
        try{
            boolean addedUser = userEntityService.addUser(user);
            if(!addedUser){
                response = new ResponseEntity<>(new CustomErrorResponse("404","User already in library"), HttpStatus.BAD_REQUEST);
            }else {
                response = new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/users/{userId}")  //http://localhost:8080/api/library/users/{userId}
    public ResponseEntity<Object> updateUser(@PathVariable Long userId,
                                             @Valid @RequestBody UserEntity user){
        ResponseEntity<Object> response = null;
        try{
            UserEntity updatedUser = userEntityService.updateUser(userId, user);
            if(updatedUser != null){
                response = new ResponseEntity<>(updatedUser, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "User id: " + userId + " not found to update!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/users/{userId}/role/{roleId}")  //http://localhost:8080/api/library/users/{userId}/role/{roleId}
    public ResponseEntity<Object> assignUserToRole(@PathVariable Long userId,
                                                     @PathVariable Long roleId){
        ResponseEntity<Object> response = null;
        try{
            UserEntity assignedUserToRole = userEntityService.assignUserToRole(userId, roleId);
            if(assignedUserToRole != null){
                response = new ResponseEntity<>(assignedUserToRole, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "User id: " + userId + ", or role id: " + roleId + " not found!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/users/{userId}")  //http://localhost:8080/api/library/users/{userId}
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        ResponseEntity<Object> response = null;
        try {
            boolean deleted = userEntityService.deleteUser(userId);
            if(!deleted) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Delete failed! User ID: " + userId + " not found."), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("User with ID: " + userId + " was deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }



}
