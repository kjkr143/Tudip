package com.tudip.miniproject.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;
    private String review;
    private double rating;
    @Column(columnDefinition = "boolean default true")
    private boolean reviewStatus;
    @Column(columnDefinition = "boolean default true")
    private boolean ratingStatus;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private Reviewer reviewer;

}
