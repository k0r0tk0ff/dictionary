
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 1/7/2018.
 */
public class WebTest {
    private static final Logger LOG = LoggerFactory.getLogger(WebTest.class);

    @Test
    public void testLogWrite() {

        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(" Test WebTest debug");
                LOG.error(" Test WebTest error");
                LOG.info(" Test WebTest info ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
