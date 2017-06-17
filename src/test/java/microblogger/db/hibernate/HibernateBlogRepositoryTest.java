package microblogger.db.hibernate;

import microblogger.domain.Blog;
import microblogger.domain.Blogger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateRepositoryTestConfig.class)
@Ignore
public class HibernateBlogRepositoryTest {
    @Autowired
    HibernateBlogRepository blogRepository;

    @Test
    @Transactional
    public void findRecent() {
        // default case
        {
            List<Blog> recent = blogRepository.findRecent();
            assertRecent(recent, 10);
        }

        // specific count case
        {
            List<Blog> recent = blogRepository.findRecent(5);
            assertRecent(recent, 5);
        }
    }

    @Test
    @Transactional
    public void findOne() {
        Blog thirteen = blogRepository.findOne(13);
        assertEquals(13, thirteen.getId().longValue());
        assertEquals("Bonjour from rst!", thirteen.getMessage());
        assertEquals(1490448900000L, thirteen.getCreateTime().getTime());
        assertEquals(4, thirteen.getBlogger().getId().longValue());
        assertEquals("rst", thirteen.getBlogger().getUsername());
        assertEquals("password", thirteen.getBlogger().getPassword());
        assertEquals("rst", thirteen.getBlogger().getFirstName());
        assertEquals("rst@xyz.com", thirteen.getBlogger().getEmail());
    }

    @Test
    @Transactional
    public void findByBlogger() {
        List<Blog> blogs = blogRepository.findByBloggerId(4L);
        assertEquals(11, blogs.size());
        for (int i = 0; i < 11; i++) {
            assertEquals(15 - i, blogs.get(i).getId().longValue());
        }
    }

    @Test
    @Transactional
    public void save() {
        Blogger blogger = blogRepository.findOne(13).getBlogger();
        Blog blog = new Blog(null, "Un Nuevo Blog from rst",
                new Date(), blogger);
        Blog saved = blogRepository.save(blog);
        assertNewBlog(saved);
        assertNewBlog(blogRepository.findOne(16L));
    }

    private void assertRecent(List<Blog> recent, int count) {
        long[] recentIds = new long[] { 3, 2, 1, 15, 14, 13, 12, 11, 10, 9 };
        assertEquals(count, recent.size());
        for (int i = 0; i < count; i++) {
            assertEquals(recentIds[i], recent.get(i).getId().longValue());
        }
    }

    private void assertNewBlog(Blog blog) {
        assertEquals(16, blog.getId().longValue());
    }

}
