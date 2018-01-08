import businesslogic.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private Logger log  = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Main main = new Main();
        main.log.error("error from class Main");
        main.log.info("info from class Main");

    }

    void doSomeTranslate(String asdf) {

        YandexApiExe ya = YandexApiExe.getInstance();
        try {
            System.out.println(ya.doGetTranslatedWord(asdf));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
