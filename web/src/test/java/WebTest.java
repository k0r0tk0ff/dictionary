
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 1/7/2018.
 */
public class WebTest {
    private static final Logger log = LoggerFactory.getLogger(WebTest.class);

    @Test
    public void testLogWrite() {



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
