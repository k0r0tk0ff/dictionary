package exe;

import launcher.TomcatLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exe {

    private static final Logger LOG  = LoggerFactory.getLogger(Exe.class);

    public static void main(String[] args) {

        try {
            TomcatLauncher launcher = new TomcatLauncher();
            launcher.launch();
        } catch (Exception e) {
            LOG.error(e.toString(), e);
        }

    }
}
