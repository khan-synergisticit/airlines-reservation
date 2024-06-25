package com.synergisticit.AirlinesReservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    @Transient
    private String confirmPassword;

    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    public User(Long userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Long userId, String username, String password, String confirmPassword, String email, List<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.roles = roles;
    }


    public void makeLikeRoles(List<Role> roleList){
        List<Role> newRoles = new ArrayList<>();
        for(Role role : roles){
            for(Role role1 : roleList){
                if(role.getRoleName().equals(role1.getRoleName())){
                    newRoles.add(role1);
                }
            }
        }
        this.roles = newRoles;
    }
}
