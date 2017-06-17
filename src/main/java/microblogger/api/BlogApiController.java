package microblogger.api;

import microblogger.db.jpa.BlogRepository;
import microblogger.domain.Blog;
import microblogger.db.BlogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogApiController {
    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    private BlogRepository blogRepository;

    @Autowired
    public BlogApiController(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * return a list of blogs whose IDs are less than the ID of last blog on the current page.
     * */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Blog> blogs(
            @RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count", defaultValue = "20") int count) {
        return blogRepository.findBlogs(max, count);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
    public Blog blogById(@PathVariable Long id) {
        return blogRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Blog> saveBlog(@RequestBody Blog blog, UriComponentsBuilder ucb) {
        Blog saved = blogRepository.save(blog);

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path("/blogs/")
                .path(String.valueOf(saved.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);

        ResponseEntity<Blog> responseEntity = new ResponseEntity<>(saved, headers, HttpStatus.CREATED);
        return responseEntity;
    }

    @ExceptionHandler(BlogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error blogNotFound(BlogNotFoundException e) {
        long blogId = e.getBlogId();
        return new Error(4, "Blog [" + blogId + "] not found");
    }
}
