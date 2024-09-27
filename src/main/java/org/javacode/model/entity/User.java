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
@Builder
@ToString(exclude = "orders")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}