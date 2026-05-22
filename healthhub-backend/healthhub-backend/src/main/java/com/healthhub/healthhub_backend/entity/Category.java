package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true,length = 80)
    private String name;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
