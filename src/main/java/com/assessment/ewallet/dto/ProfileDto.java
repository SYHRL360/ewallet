package com.assessment.ewallet.dto;

import com.assessment.ewallet.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;
}
