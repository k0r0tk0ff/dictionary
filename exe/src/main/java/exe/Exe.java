package exe;

import launcher.TomcatLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exe {
    private static final Logger LOG  = LoggerFactory.getLogger(Exe.class);

    public static void main(String[] args) {

        boolean flag = false;

        while (!flag) {
            String entered_operation;
            EnterFromKeyboard enter = new EnterFromKeyboard();

            enter.describeLogic();
            entered_operation = enter.enterFromkeyboard();
            switch (entered_operation) {
                case "0":
                    flag = true;
                    System.exit(0);
                case "1":

                    try {
                        TomcatLauncher tomcatLauncher = new TomcatLauncher();
                        tomcatLauncher.launch();
                    } catch (Exception e) {
                        LOG.error(e.toString(), e);
                    }
                    flag = true;
            }
        }
    }
}
