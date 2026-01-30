package net.javaguides.banking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
@Entity
public class Account {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name", nullable=false)
    private String accountHolderName;

    private double balance;



    @ManyToOne
    @JoinColumn(name="customer_id", nullable=true)
    private Customer customer;

    @ManyToOne

    @JoinColumn(name="bank_id", nullable=true)
    private Bank bank;

}
