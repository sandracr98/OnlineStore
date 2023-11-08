package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthdate;


    @Column( length = 30,unique = true)
    private String email;

    @Column(length = 60)
    private String pass;

    private boolean enabled;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ClientsAddress> clientsAddresses;


    @ManyToMany(mappedBy = "users",
            cascade = {CascadeType.ALL}, //hay que revisarlo pq se borran todos los roles
            fetch = FetchType.EAGER)
    private List<Role> roles;


    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<Order>();


    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addAddress(ClientsAddress clientsAddress) {
        clientsAddresses.add(clientsAddress);
        clientsAddress.setUser(this);
    }

    @Override
    public String toString() {
        return  name + ' ' + surname;
    }
}
