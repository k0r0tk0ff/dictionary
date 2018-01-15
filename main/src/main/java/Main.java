//import businesslogic.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOG  = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        //Main main = new Main();
        //LOG.error("error from class Main");
        //LOG.info("info from class Main");

        //main.doSomeTranslate("cat");
        System.setProperty("-Dlog4j.configurationFile", "log4j2.xml");
       // System.getProperties().list(System.out);

        //LOG.info("info from class Main");
        LOG.error("error from class Main");
    }

/*    void doSomeTranslate(String asdf) {

        YandexApiExe ya = YandexApiExe.getInstance();
        try {
            System.out.println(ya.doGetTranslatedWord(asdf));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
