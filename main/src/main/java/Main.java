

import businesslogic.YandexApiExe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {
    //private static Logger log = Logger.getLogger(Main.class);
    Logger log  = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
/*        Main main = new Main();
        String asdf = "town";
        main.doSomeTranslate(asdf);


        //PropertyConfigurator.configure("log4j2.xml");
        PropertyConfigurator.configure(main.input);

        log.error("error from class Main");*/

        Main main = new Main();

/*        String out;
        out = main.getPropertyValue("log4j.appender.file.MaxFileSize");
        System.out.println(out);*/

        main.log.error("error from class Main");


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
