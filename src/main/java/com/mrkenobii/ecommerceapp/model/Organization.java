package com.mrkenobii.ecommerceapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "organization")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "domain")
    private String domain;
    @Column(name = "full_name")
    private String fullName;
}
