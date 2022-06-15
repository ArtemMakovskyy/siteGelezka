package com.example.mag0422.entity.brain;

import com.example.mag0422.entity.parts.servisePartEntity.PartCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//@Entity
//@Table(name = "brain_db")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Brain {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brain_seq")
//    @SequenceGenerator(name = "brain_seq",allocationSize = 3)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Long id;
//    @Column(name = "category_id")
    private int categoryId;
    private String code;
    private String group;
    private String article;
    private String vendor;
    private String model;
    private String name;
    private String description;
//    @Column(name = "price_usd")
    private double priceUsd;
//    @Column(name = "price_ind")
    private boolean price_ind;
//    @Column(name = "category_name")
    private String categoryName;
    private boolean bonus;
//    @Column(name = "recommended_price")
    private double recommendedPrice;
    private boolean ddp;
    private int warranty;
    private boolean stock;
    private String note;
//    @Column(name = "day_delivery")
    private int dayDelivery;
//    @Column(name = "product_id")
    private int productId;
    private String url;
    private String uktved;
//    @Column(name = "group_id")
    private int groupId;
//    @Column(name = "class_id")
    private int classId;
//    @Column(name = "class_name")
    private String className;
    private int available;
    private String country;
//    @Column(name = "retail_price")
    private int retailPrice;
//    @Column(name = "cost_delivery")
    private int costDelivery;
    private boolean exclusive;
    private boolean fop;
}