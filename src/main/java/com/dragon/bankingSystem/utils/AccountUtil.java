package com.dragon.bankingSystem.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtil {

    //  ************  write a function for random accountNumber generation **************
    //account number is a concatenation between current year + any random six digits
    public static String generateAccountNumber() {

        String year = String.valueOf(Year.now().getValue());
        Random random = new Random();
        int randomSixDigits = 100000 + random.nextInt(900000);
        return year + randomSixDigits;
    }

}
