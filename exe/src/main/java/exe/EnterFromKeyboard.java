package exe;

import java.util.Scanner;

public class EnterFromKeyboard {

    public void describeLogic() {
        System.out.println("\n\n Translator API");
        System.out.println("Enter value for run - ");
        System.out.println("0 - Exit");
        System.out.println("1 - Yandex Api. Need file \"yandexapi.properties\" with key in directory, contain executable jar file");
    }

    String enterFromkeyboard () {
        String entered_operation;
        Scanner reader = new Scanner(System.in);
        entered_operation = reader.next();
        return entered_operation;
    }
}
