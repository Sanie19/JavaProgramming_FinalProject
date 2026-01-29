package net.javaguides.banking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=16)
    private String cardNumber;

    @Column(nullable=false)
    private String cardHolderName;

    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
}
