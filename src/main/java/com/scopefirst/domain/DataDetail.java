package com.scopefirst.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
Created by Joharry Correa 16/09/2018
 */

@Data
@Entity
public class DataDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    private DataTrack trackObject;

    @OneToOne
    private DataInput dataInput;

    @Enumerated(value = EnumType.STRING)
    private SessionPeriod sessionPeriod;

    private String hourInit;

    private String hourFin;

    private Date dateCreated;

}
