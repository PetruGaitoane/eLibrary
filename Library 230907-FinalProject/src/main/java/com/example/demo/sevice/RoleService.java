package com.example.demo.sevice;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    @Autowired
    public RoleService (RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(Long roleId){
        return roleRepository.findById(roleId).orElse(null);
    }
    public Boolean createNewRole (Role role){
        List<Role> roleList = roleRepository.findAll();
        if(roleList.stream().anyMatch(addrole -> addrole.getRoleName().equals(role.getRoleName()))) {
            return false;
        }
        roleRepository.saveAndFlush(role);
        return true;

    }

    public List<Role> getRoleWithFilters(Map<String, Object> params) {
        if(params.size() == 0){
            return roleRepository.findAll();
        } else if(params.containsKey("role")){
            return roleRepository.findRoleByRoleNameIgnoreCase((String) params.get("role"));
        } else {
            return null;
        }
    }

    public Role updateRole (Long roleId, Role updatedRole) {
        return roleRepository.findById(roleId).map( role -> { role.setRoleName(updatedRole.getRoleName());
                                                            return roleRepository.saveAndFlush(role);
        }).orElse(null);

    }

    public Boolean  deleteRole (Long roleId) {

        List<Role> roleList = roleRepository.findAll();
        // !!!!change to find by id;
        if(roleList.stream().anyMatch(role -> role.getRoleId().equals(roleId))){
            roleRepository.deleteById(roleId);
            return true;
        }
        return false;
    }
}
