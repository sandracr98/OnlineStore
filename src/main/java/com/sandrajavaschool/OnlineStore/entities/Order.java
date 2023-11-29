package com.sandrajavaschool.OnlineStore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentMethod_id", referencedColumnName = "id_paymentMethod")
    private PaymentMethod paymentMethod;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL) //al estar en cascada en el controlador basta con agregar los items a la factura, y se guardará todo solo
    @JoinColumn(name = "order_id")
    private List<ReceiptLine> receiptLines;


    private String deliveryMethod;
    private String goods;
    private String paymentStatus;
    private String orderStatus;
    private double sum;
    private String description;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;



    //this method let you to adding lines to the order
    public void addReceiptLine(ReceiptLine line) {
        if (this.receiptLines == null) {
            this.receiptLines = new ArrayList<>();
        }
        this.receiptLines.add(line);
    }

    public void addReceiptLines(List<ReceiptLine> linesToAdd) {
        if (this.receiptLines == null) {
            this.receiptLines = new ArrayList<>();
        }
        this.receiptLines.addAll(linesToAdd);
    }



    //This method let you to calculating the total import of a Receipt

    public Double getTotal() {
        double total = 0.0;

        int size = receiptLines.size();

        for (int i = 0; i < size; i++) {
            total = total + receiptLines.get(i).importCalc();
        }
        return total;

    }



    //This method determinate the date before the order was created
    @PrePersist
    public void prePersist() {
        date = new Date();
    }


}
