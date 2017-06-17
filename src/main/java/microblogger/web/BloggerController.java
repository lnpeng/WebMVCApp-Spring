package microblogger.web;

import microblogger.domain.Blogger;
import microblogger.db.jpa.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/microblogger")
public class BloggerController {
    private BloggerRepository bloggerRepository;

    @Autowired
    public BloggerController(BloggerRepository bloggerRepository) {
        this.bloggerRepository = bloggerRepository;
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute(new Blogger());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = POST)
    public String processRegistration(
            @Valid BloggerForm bloggerForm,
            RedirectAttributes model,
            Errors errors) throws IllegalStateException, IOException {
        if (errors.hasErrors()) {
            return "registerForm";
        }

        Blogger blogger = bloggerForm.toBlogger();
        bloggerRepository.save(blogger);
        MultipartFile profilePicture = bloggerForm.getProfilePicture();
        profilePicture.transferTo(new File("/tmp/microblogger/" + blogger.getUsername() + ".jpg"));
        model.addAttribute("username", blogger.getUsername());
        model.addFlashAttribute("blogger", blogger);
        return "redirect:/microblogger/{username}";
    }

    @RequestMapping(value = "/{username}", method = GET)
    public String showBloggerProfile(
            @PathVariable String username, Model model) {
        if (!model.containsAttribute("blogger")) {
            Blogger blogger = bloggerRepository.findByUsername(username);
            model.addAttribute(blogger);
        }
        return "profile";
    }
}
