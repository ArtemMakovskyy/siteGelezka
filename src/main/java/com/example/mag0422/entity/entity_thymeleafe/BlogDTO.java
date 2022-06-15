package com.example.mag0422.entity.entity_thymeleafe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    long id;
    String title;
    String body;
    String category;
    String author;
    Date publishedDate;
}
