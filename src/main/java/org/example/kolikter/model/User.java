package org.example.kolikter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    String full_name;
    int age;
    String city;
    String phone_num;
    String login;
    String password;

}
