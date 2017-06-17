package microblogger.db.jdbc;

import microblogger.db.BlogNotFoundException;
import microblogger.domain.Blog;
import microblogger.domain.Blogger;
import microblogger.db.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcBlogRepository implements BlogRepository {
    private final static String SELECT_BLOGS = "select bl.id, bl.blogger, bl.message, bl.created_at, b.username, b.password, b.email, b.first_name, b.last_name from blog bl, blogger b where bl.blogger = b.id";
    private final static String SELECT_BLOG_BY_ID = SELECT_BLOGS + " and bl.id = ?";
    private final static String SELECT_RECENT_BLOGS = SELECT_BLOGS + " order by bl.created_at desc limit ?";
    private final static String SELECT_RECENT_BLOGS_BEFORE_ID = SELECT_BLOGS + " and bl.id < ? order by bl.created_at desc limit ?";
    private final static String SELECT_BLOGS_BY_BLOGGER_ID = SELECT_BLOGS + " and b.id = ? order by bl.created_at desc";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBlogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Blog> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Blog> findRecent(int count) {
        return jdbcTemplate.query(SELECT_RECENT_BLOGS, new BlogRowMapper(), count);
    }

    @Override
    public List<Blog> findByBloggerId(long id) {
        return jdbcTemplate.query(SELECT_BLOGS_BY_BLOGGER_ID, new BlogRowMapper(), id);
    }

    @Override
    public Blog findOne(long id){
        try {
            return jdbcTemplate.queryForObject(SELECT_BLOG_BY_ID, new BlogRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new BlogNotFoundException(id);
        }

    }

    @Override
    public Blog save(Blog blog) {
        long blogId = insertBlogAndReturnId(blog);
        return new Blog(blogId, blog.getMessage(), blog.getCreateTime(), blog.getBlogger());
    }

    @Override
    public List<Blog> findBlogs(long max, int count) {
        return jdbcTemplate.query(SELECT_RECENT_BLOGS_BEFORE_ID, new BlogRowMapper(), max, count);
    }

    private long insertBlogAndReturnId(Blog blog) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("blog");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<>();
        args.put("blogger", blog.getBlogger().getId());
        args.put("message", blog.getMessage());
        args.put("created_at", blog.getCreateTime());
        long blogId = jdbcInsert.executeAndReturnKey(args).longValue();
        return blogId;
    }

    private static class BlogRowMapper implements RowMapper<Blog> {
        public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            Date createDate = rs.getDate("created_at");
            long bloggerId = rs.getLong("blogger");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            Blogger blogger = new Blogger(bloggerId, username, password, firstName, lastName, email);
            return new Blog(id, message, createDate, blogger);
        }
    }
}
