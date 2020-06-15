package se.sjung3;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import org.owasp.encoder.Encode;

public class Frontend {

    public static void runJavalin() {
        Javalin app = Javalin.create(config -> {
            config.enableDevLogging();
            config.addStaticFiles("static", Location.EXTERNAL);
        }).start("localhost", 7000);

        app.before(context -> {
            context.contentType("text/html; charset=UTF-8");
        });

        app.get("/", context -> {
            startPage(context);
        });

        app.post("/result", context -> {
            Control.controlStart(context);
        });
    }

    private static void startPage(Context context) {

        String content =
                "<h1>The Social Security Number Validator</h1>" +
                        "<p class='instruction'>Enter a Swedish Social Security Number<p>";

        content +=
                "<form method='post' action='/result'>" +
                        "<label>" +
                        "<div id='input'>" +
                        "<input type='text' name='ssn' placeholder='yyyymmdd-xxxx'>" +
                        "<button>Submit</button>" +
                        "</div>" +
                        "</label>" +
                        "</form>";

        String html = template(content);
        context.result(html);
    }

    public static void redirectToMainPage(String reason, Context context) {
        String content = "<div class='result'>" +
                "<h1 id='reason'>" + Encode.forHtml(reason) + "</h1>" +
                "<a class='redirect' href='/'><input class='redirect' type='button' value='Return to main page'></a>" +
                "</div>";
        String html = template(content);
        context.result(html);
        ;
    }

    private static String template(String content) {
        return
                "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<link rel='stylesheet' href='/css/style.css'> " +
                        "<link rel= 'icon' type= 'image/png' href='/favicon/favicon.ico'>" +
                        "<title>SSN Validator</title>" +
                        "</head>" +
                        "<body>" +
                        content +
                        "</body>" +
                        "</html>";
    }

}
