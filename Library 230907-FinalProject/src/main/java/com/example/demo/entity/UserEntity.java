package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    @Column(name = "FirstName")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{2,}$")
    private String userFirstName;
    @Column(name = "LastName")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{2,}$")
    private String userLastName;
    @Column(name = "UserName")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z_0-9 ]{2,}$")
    private String userName;
    @Column(name = "Password")
    @NotNull
    private String userPassword;
    @Column(name = "ContactNumber")
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private int userContactNumber;
    @Column(name = "Email")
    @NotNull
    @Pattern( regexp = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\n")
    private String userEmail;
    @ManyToMany
    @JoinTable (name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();;

    public UserEntity() {

    }

    public UserEntity(String userFirstName, String userLastName, String userName, String userPassword, int userContactNumber, String userEmail, Set<Role> roles) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userContactNumber = userContactNumber;
        this.userEmail = userEmail;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(int userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
