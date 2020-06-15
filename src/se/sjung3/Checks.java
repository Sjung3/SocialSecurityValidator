package se.sjung3;

import org.apache.commons.validator.GenericValidator;
import java.util.ArrayList;

public class Checks {

    public static Boolean formatValidation(String socialSecurityNumberToCheck) {
        String pattern = "^(\\d{2})(\\d{2})(\\d{2})(\\d{2})([-|+]?)?(\\d{4})$";
        if (socialSecurityNumberToCheck.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }

    public static String removeSpecialCharacter(String socialSecurityNumber) {
        if (socialSecurityNumber.contains("-") || socialSecurityNumber.contains("+")) {
            socialSecurityNumber = socialSecurityNumber.replace("-", "").replace("+", "");
            return socialSecurityNumber;
        } else {
            return socialSecurityNumber;
        }
    }

    public static String getDateOfBirth(String yourSocialSecurityNumber) {
        String dateOfBirth = yourSocialSecurityNumber.substring(0, 8);
        return dateOfBirth;
    }

    public static int getControlDigit(String ssn) {
        String lastDigit = ssn.substring(11);
        int controlDigit = Integer.parseInt(lastDigit);
        return controlDigit;
    }

    public static int getFirstDigits(String socialSecurityNumber) {
        String digitsNeeded = socialSecurityNumber.substring(2, 11);
        int firstDigits = Integer.parseInt(digitsNeeded);
        return firstDigits;
    }

    public static Boolean dateValidation(String dateOfBirth) {
        if (GenericValidator.isDate(dateOfBirth, "yyyyMMdd", true)) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean finalValidation(int controlDigit, int firstDigits) {
        ArrayList<Integer> digitsToSum = getDigitsToSum(firstDigits);
        int controlSum = sumOfProducts(digitsToSum);
        int roundUp = (int) Math.ceil(controlSum / 10.0) * 10;
        int controlAgainst = roundUp - controlSum;

        if (controlAgainst == controlDigit) {
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<Integer> getDigitsToSum(int firstDigitsOfSocialSecurityNumber) {
        ArrayList<Integer> allNewDigits = new ArrayList<Integer>();
        ArrayList<Integer> multiplyByTwo = new ArrayList<Integer>();
        ArrayList<Integer> multiplyByOne = new ArrayList<Integer>();

        String firstDigits = Integer.toString(firstDigitsOfSocialSecurityNumber);

        for (int i = 0; i < firstDigits.length(); i += 1) {
            if (1 == i % 2) {
                multiplyByOne.add(Integer.parseInt(Character.toString(firstDigits.charAt(i))));
            } else {
                multiplyByTwo.add(Integer.parseInt(Character.toString(firstDigits.charAt(i))));
            }
        }

        for (int i : multiplyByOne) {
            allNewDigits.add(i);
        }
        for (int i : multiplyByTwo) {
            int product = i * 2;
            if (product < 10) {
                allNewDigits.add(product);
            } else {
                int nr1 = product / 10 % 10;
                int nr2 = product % 10;
                allNewDigits.add(nr1);
                allNewDigits.add(nr2);
            }
        }
        return allNewDigits;
    }

    //Sum of all single digits
    private static int sumOfProducts(ArrayList<Integer> digitsToCheckAgainsControlNumber) {
        int controlSum = 0;
        for (int i = 0; i < digitsToCheckAgainsControlNumber.size(); i++) {
            controlSum = controlSum += digitsToCheckAgainsControlNumber.get(i);
        }
        return controlSum;
    }
}
