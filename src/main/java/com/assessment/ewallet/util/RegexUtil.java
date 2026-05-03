package com.assessment.ewallet.util;

import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^(.+)@(\\S+)$");
        return pattern.matcher(email).matches();
    }


}
