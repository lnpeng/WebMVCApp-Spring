package microblogger.db.jpa;

import microblogger.domain.Blog;

import java.util.List;

public interface BlogRepositoryCustom {

    List<Blog> findRecent();

    List<Blog> findRecent(int count);

    List<Blog> findBlogs(long max, int count);
}
