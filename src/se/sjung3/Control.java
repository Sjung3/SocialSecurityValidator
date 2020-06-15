package se.sjung3;

import io.javalin.http.Context;
import java.io.IOException;

public class Control {

    public static void controlStart(Context context) throws IOException {
        String socialSecurityNumber = context.formParam("ssn");
        Boolean formatIsValid = Checks.formatValidation(socialSecurityNumber);
        controlDate(context, socialSecurityNumber, formatIsValid);
    }

    public static void controlDate(Context context, String socialSecurityNumber, Boolean formatIsValid) {
        Boolean dateIsValid = null;
        if (formatIsValid == true) {
            socialSecurityNumber = Checks.removeSpecialCharacter(socialSecurityNumber);
            String dateOfBirth = Checks.getDateOfBirth(socialSecurityNumber);
            dateIsValid = Checks.dateValidation(dateOfBirth);
            controlFinal(context, socialSecurityNumber, dateIsValid);
        } else {
            String reason = "Use format YYYYMMDD-XXXX";
            Frontend.redirectToMainPage(reason, context);
            return;

        }
    }

    public static void controlFinal(Context context, String socialSecurityNumber, Boolean dateIsValid) {
        Boolean socialSecurityNumberIsValid = null;
        if (dateIsValid == true) {
            int contolDigit = Checks.getControlDigit(socialSecurityNumber);
            int firstDigitsOfsocialSecurityNumber = Checks.getFirstDigits(socialSecurityNumber);
            socialSecurityNumberIsValid = Checks.finalValidation(contolDigit, firstDigitsOfsocialSecurityNumber);
            resultMessage(context, socialSecurityNumberIsValid);
        } else {
            String reason = "Use format YYYYMMDD-XXXX";
            Frontend.redirectToMainPage(reason, context);
            return;
        }
    }

    public static void resultMessage(Context context, Boolean socialSecurityNumberIsValid) {
        if (socialSecurityNumberIsValid) {
            String reason = "The social security number is valid";
            Frontend.redirectToMainPage(reason, context);
        } else {
            String reason = "The social security number is NOT valid";
            Frontend.redirectToMainPage(reason, context);
        }
    }
}
