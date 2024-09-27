package org.javacode.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.Builder.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "products")
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Default
    @OneToMany
    @Column(nullable = false)
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

}
