package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;


    //la relacion es en un unico sentido por eso esta asi el cascade y el join
    //ademas en receiptline no esta el atributo order
    @OneToMany(cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<ReceiptLine> receiptLines;


    private String deliveryMethod;
    private String goods;
    private Boolean paymentStatus;
    private String orderStatus; //con un check box
    private double sum;
    private String description;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;



    //this method let you to adding lines to the order
    public void addReceiptLine(ReceiptLine receiptLine) {
        this.receiptLines.add(receiptLine);
    }



    //This method let you to calculating the total import of a Receipt

    public Double getTotal() {
        double total = 0.0;

        int size = receiptLines.size();

        for (int i = 0; i < size; i++) {
            total =+ receiptLines.get(i).importCalc();
        }
        return total;
    }



    //This method determinate the date before the order was created
    @PrePersist
    public void prePersist() {
        date = new Date();
    }


}
