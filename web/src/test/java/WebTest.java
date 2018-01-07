import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by user on 1/7/2018.
 */
public class WebTest {
    private static final Logger log = Logger.getLogger(WebTest.class.getName());

    @Test
    public void testLogWrite() {

        log.setLevel(Level.DEBUG);

        try {
            if (log.isDebugEnabled()) {
                log.debug(" Test WebTest debug");
                log.error(" Test WebTest error");
                log.info(" Test WebTest info ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
