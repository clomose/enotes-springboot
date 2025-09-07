package com.example.Enotes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notes extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne  //due to this category becomes category_id and check it in database
    private Category category;

    @ManyToOne  //due to this file becomes file_id and check it in database
    private FileDetails file;

    private Boolean isDeleted;

    private Date deletedOn;

//    private Integer userId;
}
