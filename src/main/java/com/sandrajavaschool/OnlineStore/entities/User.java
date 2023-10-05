package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id;

    private String name;
    private String surname;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;


    private String email;
    private String pass;

    @ManyToMany(mappedBy = "users",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();


    public void addRole(Role role){
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

}
