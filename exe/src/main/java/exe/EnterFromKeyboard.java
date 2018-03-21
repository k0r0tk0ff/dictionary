package exe;

import java.util.Scanner;

public class EnterFromKeyboard {

    public void describeLogic() {
        System.out.println("\n\n  Translator API \n\n");
        System.out.println("  Enter value for run - ");
        System.out.println("  0 - Exit");
        System.out.println("  1 - [web] Yandex Api.");
        System.out.println("  2 - [API] Yandex Api.");
        System.out.println("\n\n  WARNING!  For use  Yandex Api need put:");
        System.out.println("1) file \"yandexapi.properties\" ");
        System.out.println("contains: ");
        System.out.println("key=Value_of_key_from_yandex");
        System.out.println("\n2) [Optional] file \"proxy.properties\" ");
        System.out.println("contains: ");
        System.out.println("host=10.5.45.250");
        System.out.println("port=3128");
        System.out.println("userName=corp/korotkov_a_a");
        System.out.println("password=*********");
        System.out.println("\nin directory, contains executable jar file \n");
    }

    String enterFromkeyboard () {
        String entered_operation;
        Scanner reader = new Scanner(System.in);
        entered_operation = reader.next();
        return entered_operation;
    }
}
