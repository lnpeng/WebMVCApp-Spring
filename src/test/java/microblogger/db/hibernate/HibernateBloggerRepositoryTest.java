package microblogger.db.hibernate;

import microblogger.domain.Blogger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=HibernateRepositoryTestConfig.class)
@Ignore
public class HibernateBloggerRepositoryTest {
    @Autowired
    HibernateBloggerRepository bloggerRepository;

    @Test
    @Transactional
    public void findByUsername() {
        assertBlogger(0, bloggerRepository.findByUsername("abc"));
        assertBlogger(1, bloggerRepository.findByUsername("efg"));
        assertBlogger(2, bloggerRepository.findByUsername("opq"));
        assertBlogger(3, bloggerRepository.findByUsername("rst"));
    }

    @Test
    @Transactional
    public void save_newBlogger() {
        Blogger blogger = new Blogger(null, "newbee", "letmein", "New",
                "Bee", "newbee@xyz.com");
        Blogger saved = bloggerRepository.save(blogger);
        assertBlogger(4, saved);
        assertBlogger(4, bloggerRepository.findByUsername("newbee"));
    }

    private static void assertBlogger(int expectedBloggerIndex, Blogger actual) {
        Blogger expected = BLOGGERS[expectedBloggerIndex];
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    private static Blogger[] BLOGGERS = new Blogger[6];

    @BeforeClass
    public static void before() {
        BLOGGERS[0] = new Blogger(1L, "abc", "password", "abc",
                "abc", "abc@xyz.com");
        BLOGGERS[1] = new Blogger(2L, "efg", "password", "efg",
                "efg", "efg@xyz.com");
        BLOGGERS[2] = new Blogger(3L, "opq", "password", "opq",
                "opq", "opq@xyz.com");
        BLOGGERS[3] = new Blogger(4L, "rst", "password", "rst",
                "rst", "rst@xyz.com");
        BLOGGERS[4] = new Blogger(5L, "newbee", "letmein", "New",
                "Bee", "newbee@xyz.com");
        BLOGGERS[5] = new Blogger(4L, "rst", "letmein", "rst",
                "rst", "rst@xyz.com");
    }
}
