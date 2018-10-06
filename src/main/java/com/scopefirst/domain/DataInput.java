package com.scopefirst.domain;


import lombok.Data;

import javax.persistence.*;

/*
Created by Joharry Correa 16/09/2018
 */

@Data
@Entity
public class DataInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private Integer timeActivity;

    /*
    @OneToOne(cascade = CascadeType.ALL)
    private DataDetail dataDetail;
    */

}
