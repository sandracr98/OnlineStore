package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients_Addresses")
public class ClientsAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String city;
    private String postal_code;
    private String street;
    private String home;
    private String apartment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;


}
