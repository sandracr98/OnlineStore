package com.sandrajavaschool.OnlineStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private String volume;
    private Integer stock;
    private String brand;
    private String color;
    private String weight;

    private String photo;

    private Integer totalSales = 0;

    private boolean status;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id_category")
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product",
    cascade = {CascadeType.ALL},
    fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ReceiptLine> receiptLine;


    @PrePersist
    public void prePersist() {
        date = new Date();
    }



}
