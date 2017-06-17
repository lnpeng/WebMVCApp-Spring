package microblogger.web;

import microblogger.domain.Blogger;
import microblogger.db.jpa.BloggerRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class BloggerControllerTest {
    @Test
    public void shouldShowRegisteration() throws Exception {
        BloggerRepository mockRepository = mock(BloggerRepository.class);
        BloggerController controller = new BloggerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/microblogger/register"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    @Ignore
    public void shouldProcessRegistration() throws Exception {
        BloggerRepository mockRepository = mock(BloggerRepository.class);
        Blogger unsaved = new Blogger("nbauer", "24hours", "Natalie", "Bauer", "nbauer@xyz.org");
        Blogger saved = new Blogger(24L, "nbauer", "24hours", "Natalie", "Bauer", "nbauer@xyz.org");
        when(mockRepository.save(unsaved)).thenReturn(saved);

        BloggerController controller = new BloggerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/microblogger/register")
                .param("firstName", "Natalie")
                .param("lastName", "Bauer")
                .param("username", "nbauer")
                .param("password", "24hours")
                .param("email", "nbauer@xyz.org"))
                .andExpect(redirectedUrl("/microblogger/nbauer"));

        verify(mockRepository, atLeastOnce()).save(unsaved);
    }

    @Test
    @Ignore
    public void shouldFailValidationWithNoData() throws Exception {
        BloggerRepository mockRepository = mock(BloggerRepository.class);
        BloggerController controller = new BloggerController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/microblogger/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerForm"))
                .andExpect(model().errorCount(5))
                .andExpect(model().attributeHasFieldErrors(
                        "blogger", "firstName", "lastName", "username", "password", "email"));
    }
}
