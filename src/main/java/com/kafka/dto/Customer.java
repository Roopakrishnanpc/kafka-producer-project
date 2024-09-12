package com.kafka.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
   // @CsvBindByName(column = "ip_address")
    private String ipAddress;
//private int age;
//private int contactNo;

//
//@CsvBindByName(column = "id")
//private Integer id;
//
//@CsvBindByName(column = "firstName")
//private String fstname;
//
//@CsvBindByName(column = "lastName")
//private String lstname;
//
//@CsvBindByName(column = "email")
//private String email;
//
//@CsvBindByName(column = "gender")
//private String gender;
//
//@CsvBindByName(column = "ip_address")
//private String ipAddress;

}
