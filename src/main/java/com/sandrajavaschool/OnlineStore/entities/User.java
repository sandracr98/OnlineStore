package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
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

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    @Size(min = 4, max = 30)
    private String name;

    @NotEmpty
    private String surname;

    @Temporal(TemporalType.DATE)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Column(length = 30, unique = true)
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(length = 60)
    @NotEmpty(message = "Password is required")
    private String pass;

    @NotNull
    private boolean enabled = true;

    private String photo;

    private double totalSpent;


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ClientsAddress> clientsAddresses;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "Users_has_Roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private List<Role> roles;


    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<Order>();


    public void addAddress(ClientsAddress clientsAddress) {
        if (clientsAddress != null) {
            clientsAddresses.add(clientsAddress);
            clientsAddress.setUser(this);
        }
    }

    @Override
    public String toString() {
        return name + ' ' + surname;
    }
}
