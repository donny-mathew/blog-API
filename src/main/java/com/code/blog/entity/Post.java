package com.code.blog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Column(name = "description", nullable = false)
    @NotEmpty
    @Size(min = 5, message = "Description should have at least 5 characters")
    private String description;

    @Column(name = "content", nullable = false)
    @NotEmpty
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<Comment> comments;
}
