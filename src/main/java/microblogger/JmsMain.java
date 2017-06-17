package microblogger;

import microblogger.domain.Blog;
import microblogger.domain.Blogger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsOperations;

import java.util.Date;

public class JmsMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/message.xml");
        JmsOperations jms = context.getBean(JmsOperations.class);
        Blogger blogger = new Blogger(null, "newbee", "letmein", "New",
                "Bee", "newbee@xyz.com");
        Blog blog = new Blog(1L, "Hello", new Date(), blogger);
        for(int i=0; i< 10; i++) {
            jms.convertAndSend("hello.queue", blog);
        }
        for (int i = 0; i < 10; i++) {
            Blog o = (Blog)jms.receiveAndConvert("hello.queue");
            System.out.println(o.getMessage());
        }
        context.close();
    }
}
