package org.example.kolikter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String full_name;
    private int age;
    private String city;
    private String phone_num;
    private String login;
    private String password;

    public static boolean isAdmin = false;

}
