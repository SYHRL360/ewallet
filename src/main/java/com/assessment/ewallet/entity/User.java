package com.assessment.ewallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
