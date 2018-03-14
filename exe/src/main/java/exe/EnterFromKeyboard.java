package exe;

import java.util.Scanner;

public class EnterFromKeyboard {

    public void describeLogic() {
        System.out.println("\n\n  Translator API \n\n");
        System.out.println("  Enter value for run - ");
        System.out.println("  0 - Exit");
        System.out.println("  1 - [web] Yandex Api.");
        System.out.println("  2 - [API] Yandex Api.");
        System.out.println("\n\n  WARNING!  For use  Yandex Api need put file \"yandexapi.properties\" ");
        System.out.println("  with key in directory, contains executable jar file \n");
    }

    String enterFromkeyboard () {
        String entered_operation;
        Scanner reader = new Scanner(System.in);
        entered_operation = reader.next();
        return entered_operation;
    }
}
