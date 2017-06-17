package microblogger.alert;

import microblogger.domain.Blog;

public interface AlertService {
    void sendBlogAlert(Blog blog);

    Blog receiveBlogAlert();
}
