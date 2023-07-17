package com.Wipro.Student.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private int doorNumber;
    private String colony;
    private String city;
    private String state;
    private String country;
    private Long pinCode;
}
