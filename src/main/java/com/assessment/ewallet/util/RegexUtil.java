package com.assessment.ewallet.util;

import java.util.regex.Pattern;

public class RegexUtil {



    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$");
        return pattern.matcher(email).matches();
    }


}
