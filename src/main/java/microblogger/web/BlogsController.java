package microblogger.web;

import microblogger.alert.AlertService;
import microblogger.domain.Blog;
import microblogger.domain.Blogger;
import microblogger.db.jpa.BlogRepository;
import microblogger.db.jpa.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/blogs")
public class BlogsController {
    private BlogRepository blogRepository;
    private BloggerRepository bloggerRepository;
    private AlertService alertService;

    @Autowired
    public BlogsController(BlogRepository blogRepository, BloggerRepository bloggerRepository, AlertService alertService) {
        this.blogRepository = blogRepository;
        this.bloggerRepository = bloggerRepository;
        this.alertService = alertService;
    }

    @RequestMapping(method = GET)
    public List<Blog> blogs(
            @RequestParam(value = "count", defaultValue = "20") int count) {
        return blogRepository.findRecent(count);
    }

    @RequestMapping(value = "/{blogId}", method = GET)
    public String blog(
            @PathVariable("blogId") long blogId,
            Model model) {
        Blog blog = blogRepository.findById(blogId);
        model.addAttribute(blog);
        return "blog";
    }

    @RequestMapping(method = POST)
    public String saveBlog(BlogForm form, Model model, Principal principal) throws Exception {
        Blogger blogger = null;
        if (!model.containsAttribute("blogger")) {
            String username = principal.getName();
            blogger = bloggerRepository.findByUsername(username);
        }
        else {
            blogger = (Blogger)model.asMap().get("blogger");
        }
        Blog blog = blogRepository.save(new Blog(null, form.getMessage(), new Date(), blogger));
        alertService.sendBlogAlert(blog);
        return "redirect:/blogs";
    }
}
