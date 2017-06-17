package microblogger.db;

import microblogger.domain.Blog;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BlogRepository {
    List<Blog> findRecent();

    List<Blog> findRecent(int count);

    @Cacheable("blogCache")
    List<Blog> findBlogs(long max, int count);

    @Cacheable("blogCache")
    List<Blog> findByBloggerId(long id);

    @Cacheable("blogCache")
    Blog findOne(long id);

    @CachePut(value="blogCache", key="#result.id")
    Blog save(Blog blog);
}
