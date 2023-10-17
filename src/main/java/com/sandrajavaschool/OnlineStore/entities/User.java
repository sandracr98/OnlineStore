package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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


    private String email;
    private String pass;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ClientsAddress> clientsAddresses;


    @ManyToMany(mappedBy = "users",
            cascade = {CascadeType.REFRESH}, //hay que revisarlo pq se borran todos los roles
            fetch = FetchType.LAZY)
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
