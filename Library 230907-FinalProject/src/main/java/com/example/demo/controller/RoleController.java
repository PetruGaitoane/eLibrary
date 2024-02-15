package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.helpers.CustomErrorResponse;
import com.example.demo.sevice.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/library") //http://localhost:8080/api/library
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/roles/{roleId}") //http://localhost:8080/api/library/roles/{roleId}
    public ResponseEntity<Object> getRoleById(@PathVariable Long roleId){
        ResponseEntity<Object> response = null;
        try{
            Role role = roleService.getRoleById(roleId);
            if(role != null){
                response = new ResponseEntity<>(role, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Role id: "+ roleId +" not found!"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/roles") //http://localhost:8080/api/library/roles
    public ResponseEntity<Object> getRoleWithFilter(@RequestParam Map<String, Object> params){
        ResponseEntity<Object> response = null;
        try{
            List<Role> roleList = roleService.getRoleWithFilters(params);
            if(roleList != null && roleList.size() > 0){
                response = new ResponseEntity<>(roleService.getRoleWithFilters(params), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Role not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
    @PostMapping ("/roles") //http://localhost:8080/api/library/roles
    public ResponseEntity<Object> createNewRole(@Valid @RequestBody Role role){
        ResponseEntity<Object> response = null;
        try{
            boolean addedRole = roleService.createNewRole(role);
            if(!addedRole){
                response = new ResponseEntity<>(new CustomErrorResponse("404","Role already in DB"), HttpStatus.BAD_REQUEST);
            }else {
                response = new ResponseEntity<>("Role added successfully", HttpStatus.CREATED);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/roles/{roleId}")  //http://localhost:8080/api/library/roles/{roleId}
    public ResponseEntity<Object> updateRole(@PathVariable Long roleId,
                                             @Valid @RequestBody Role role){
        ResponseEntity<Object> response = null;
        try{
            Role updatedRole = roleService.updateRole(roleId, role);
            if(updatedRole != null){
                response = new ResponseEntity<>(updatedRole, HttpStatus.OK);
            }else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Role id: " + roleId + " not found to update!") , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    @DeleteMapping("/roles/{roleId}")  //http://localhost:8080/api/library/roles/{roleId}
    public ResponseEntity<Object> deleteRole(@PathVariable Long roleId) {
        ResponseEntity<Object> response = null;
        try {
            boolean deleted = roleService.deleteRole(roleId);
            if(!deleted) {
                response =  new ResponseEntity<>(new CustomErrorResponse(" 404", "Delete failed! Role ID: " + roleId + " not found."), HttpStatus.NOT_FOUND);
            } else {
                response =  new ResponseEntity<>("Role with ID: " + roleId + " was deleted successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
