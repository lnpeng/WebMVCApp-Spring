package microblogger.web;

import microblogger.alert.AlertService;
import microblogger.alert.AlertServiceImpl;
import microblogger.domain.Blog;
import microblogger.db.jpa.BlogRepository;
import microblogger.db.jpa.BloggerRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class BlogsControllerTest {
    @Test
    public void shouldShowRecentBlogs() throws Exception {
        List<Blog> expectedBlogs = createBlogList(20);
        BlogRepository mockRepository = mock(BlogRepository.class);
        when(mockRepository.findRecent(20))
                .thenReturn(expectedBlogs);
        BloggerRepository mockBloggerRepository = mock(BloggerRepository.class);
        AlertService mockAlertService = mock(AlertServiceImpl.class);

        BlogsController controller = new BlogsController(mockRepository, mockBloggerRepository, mockAlertService);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/blogs.jsp"))
                .build();

        mockMvc.perform(get("/blogs"))
                .andExpect(view().name("blogs"))
                .andExpect(model().attributeExists("blogList"))
                .andExpect(model().attribute("blogList",
                        hasItems(expectedBlogs.toArray())));
    }

    @Test
    public void shouldShowPagedBlogs() throws Exception {
        List<Blog> expectedBlogs = createBlogList(50);
        BlogRepository mockRepository = mock(BlogRepository.class);
        when(mockRepository.findRecent(50))
                .thenReturn(expectedBlogs);
        BloggerRepository mockBloggerRepository = mock(BloggerRepository.class);
        AlertService mockAlertService = mock(AlertServiceImpl.class);

        BlogsController controller = new BlogsController(mockRepository, mockBloggerRepository, mockAlertService);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/blogs.jsp"))
                .build();

        mockMvc.perform(get("/blogs?max=238900&count=50"))
                .andExpect(view().name("blogs"))
                .andExpect(model().attributeExists("blogList"))
                .andExpect(model().attribute("blogList",
                        hasItems(expectedBlogs.toArray())));
    }

    @Test
    public void testBlog() throws Exception {
        Blog expectedBlog = new Blog("Hello", new Date(), null);
        BlogRepository mockRepository = mock(BlogRepository.class);
        when(mockRepository.findById(12345))
                .thenReturn(expectedBlog);
        BloggerRepository mockBloggerRepository = mock(BloggerRepository.class);
        AlertService mockAlertService = mock(AlertServiceImpl.class);

        BlogsController controller = new BlogsController(mockRepository, mockBloggerRepository, mockAlertService);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/blogs/12345"))
                .andExpect(view().name("blog"))
                .andExpect(model().attributeExists("blog"))
                .andExpect(model().attribute("blog", expectedBlog));
    }

    @Test
    public void saveBlog() throws Exception {
        BlogRepository mockRepository = mock(BlogRepository.class);
        BloggerRepository mockBloggerRepository = mock(BloggerRepository.class);
        AlertService mockAlertService = mock(AlertServiceImpl.class);
        BlogsController controller = new BlogsController(mockRepository, mockBloggerRepository, mockAlertService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        Principal principal = mock(Principal.class);

        mockMvc.perform(post("/blogs")
                .principal(principal)
                .param("message", "Hello"))
                .andExpect(redirectedUrl("/blogs"));

        verify(mockRepository, atLeastOnce()).save(any(Blog.class));
    }

    private List<Blog> createBlogList(int count) {
        List<Blog> blogs = new ArrayList<Blog>();
        for (int i=0; i < count; i++) {
            blogs.add(new Blog("Blog " + i, new Date(), null));
        }
        return blogs;
    }
}
