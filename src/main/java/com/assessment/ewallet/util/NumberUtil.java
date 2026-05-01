package com.assessment.ewallet.util;

import java.util.Random;

public final class NumberUtil {

    private static final Random rand = new Random();



    public static int generate2Number(){
        return rand.nextInt(90) + 10;
    }

}
