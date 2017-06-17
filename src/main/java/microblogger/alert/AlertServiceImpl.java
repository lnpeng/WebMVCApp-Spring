package microblogger.alert;

import microblogger.domain.Blog;
import org.springframework.jms.core.JmsOperations;

public class AlertServiceImpl implements AlertService {

    private JmsOperations jmsOperations;

    public AlertServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    @Override
    public void sendBlogAlert(Blog blog) {
        jmsOperations.convertAndSend(blog);
    }

    @Override
    public Blog receiveBlogAlert() {return (Blog)jmsOperations.receiveAndConvert();}
}
