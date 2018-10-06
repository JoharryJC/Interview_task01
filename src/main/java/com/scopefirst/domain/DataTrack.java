package com.scopefirst.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class DataTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreated;

    //@OneToMany(mappedBy = "trackObject", fetch = FetchType.LAZY)
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "trackObject", orphanRemoval = true)
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "trackObject")
    //@OneToMany(cascade = CascadeType.MERGE, mappedBy = "trackObject")
    //private Set<DataDetail> dataDetails = new HashSet<>();

}
