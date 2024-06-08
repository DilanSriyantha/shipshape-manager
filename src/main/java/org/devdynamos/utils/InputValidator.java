package org.devdynamos.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    private static final String STANDARD_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern STANDARD_EMAIL_PATTERN = Pattern.compile(STANDARD_EMAIL_REGEX);

    private static final String TEN_DIGIT_TELEPHONE_NUMBER_REGEX = "^(\\+94|0)?\\d{9}$";
    private static final Pattern TEN_DIGIT_TELEPHONE_NUMBER_PATTERN = Pattern.compile(TEN_DIGIT_TELEPHONE_NUMBER_REGEX);

    public static boolean isValidEmail(String email){
        if(email == null) return false;

        Matcher matcher = STANDARD_EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null) return false;

        Matcher matcher = TEN_DIGIT_TELEPHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
}
