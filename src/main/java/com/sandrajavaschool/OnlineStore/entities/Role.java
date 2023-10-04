package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Long id;

    private String name;

    @ManyToMany //algún cascade?
    @JoinTable(name = "Users_has_Roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user"))
    private Set<User> users = new HashSet<>();



    public void addUsers(User user){
        users.add(user);
        user.getRoles().add(this);
    }

}
