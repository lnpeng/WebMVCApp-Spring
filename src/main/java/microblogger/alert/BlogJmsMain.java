package microblogger.alert;

import microblogger.domain.Blog;
import microblogger.domain.Blogger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class BlogJmsMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/message.xml");
        AlertService alertService = context.getBean(AlertService.class);

        Blogger blogger = new Blogger(null, "newbee", "letmein", "New",
                "Bee", "newbee@xyz.com");
        Blog blog = new Blog(1L, "Hello", new Date(), blogger);
        alertService.sendBlogAlert(blog);
    }
}
