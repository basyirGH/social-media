package com.basyir.projects.socialmedia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "like")
public class Like {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

}
