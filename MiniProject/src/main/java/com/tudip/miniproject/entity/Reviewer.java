package com.tudip.miniproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Table(name = "reviewer")
@Entity
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    private String address;
    private String contact;

    @Column(columnDefinition = "boolean default true")
    private boolean status;

}
