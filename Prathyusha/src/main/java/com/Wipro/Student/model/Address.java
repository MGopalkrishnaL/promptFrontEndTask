package com.Wipro.Student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int addressId;
    private int doorNumber;
    private String colony;
    private String city;
    private String state;
    private String country;
    private Long pinCode;
    @ManyToOne
    @JsonIgnore
    private Student student;
}
