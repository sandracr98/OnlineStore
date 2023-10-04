package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Long id;

    private String title;
    private Double price;
    private String category;
    private String volume;
    private Integer stock;
    private String brand;
    private String color;
    private String weight;

    @Temporal(TemporalType.DATE)
    private Date date;


    @PrePersist
    public void prePersist() {
        date = new Date();
    }



}
