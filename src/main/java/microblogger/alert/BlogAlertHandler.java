package microblogger.alert;

import microblogger.domain.Blog;

public class BlogAlertHandler {

    public void handleBlogAlert(Blog blog) {
        System.out.println(blog.getMessage());
    }

}
