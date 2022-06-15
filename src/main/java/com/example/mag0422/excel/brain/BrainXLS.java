package com.example.mag0422.excel.brain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "brain_db")
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
public class BrainXLS {
//    https://betacode.net/11259/read-write-excel-file-in-java-using-apache-poi
//    https://www.youtube.com/watch?v=McZ77Y9jZno&list=PLwcDaxeEINafif17no5JAO0iAi9Gw4g6H&index=6
//    https://www.youtube.com/results?search_query=%D0%BA%D0%B0%D0%BA+%D0%B7%D0%B0%D0%BA%D0%B0%D1%87%D0%B0%D1%82%D1%8C+%D1%82%D0%B0%D0%B1%D0%BB%D0%B8%D1%86%D0%B5+excel+java
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brain_seq")
//    @SequenceGenerator(name = "brain_seq", allocationSize = 1)
//    @Column(name = "id", nullable = false)
//    @GeneratedValue
    private int id;

    private int categoryId;
    private String code;
//    private String group;
//    private String article;
//    private String vendor;
//    private String model;
//    private String name;
//    private String description;
//    private double priceUsd;
//    private boolean price_ind;
//    private String categoryName;
//    private boolean bonus;
//    private double recommendedPrice;
//    private boolean ddp;
//    private int warranty;
//    private boolean stock;
//    private String note;
//    private int dayDelivery;
//    private int productId;
//    private String url;
//    private int uktved;
//    private int groupId;
//    private int classId;
//    private String className;
//    private int available;
//    private String country;
//    private int retailPrice;
//    private int costDelivery;
//    private boolean exclusive;
//    private boolean fop;

    public List<BrainXLS> saveList() {
        List<BrainXLS> brainXLS = new ArrayList<>();

        return brainXLS;
    }

    public List<String> columnsName() {
        return List.of(
                "CategoryID",
                "Code",
                "Group",
                "Article",
                "Vendor",
                "Model",
                "Name",
                "Description",
                "PriceUSD",
                "Price_ind",
                "CategoryName",
                "Bonus",
                "RecommendedPrice",
                "DDP",
                "Warranty",
                "Stock",
                "Note",
                "DayDelivery",
                "ProductID",
                "URL",
                "UKTVED",
                "GroupID",
                "ClassID",
                "ClassName",
                "Available",
                "Country",
                "RetailPrice",
                "CostDelivery",
                "Exclusive",
                "FOP");
    }
}
