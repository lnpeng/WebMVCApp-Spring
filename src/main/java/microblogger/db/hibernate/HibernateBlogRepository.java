package microblogger.db.hibernate;

import microblogger.db.BlogRepository;
import microblogger.domain.Blog;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Repository
@Primary
@Transactional
public class HibernateBlogRepository implements BlogRepository {

    private SessionFactory sessionFactory;

    @Inject
    public HibernateBlogRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Blog> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Blog> findRecent(int count) {
        return blogCriteria()
                .setMaxResults(count)
                .list();
    }

    @Override
    public List<Blog> findByBloggerId(long id) {
        return blogCriteria()
                .add(Restrictions.eq("blogger.id", id))
                .list();
    }

    @Override
    public Blog findOne(long id) {
        return currentSession()
                .get(Blog.class, id);
    }

    @Override
    public Blog save(Blog blog) {
        Serializable id = currentSession().save(blog);
        return new Blog(
                (Long) id,
                blog.getMessage(),
                blog.getCreateTime(),
                blog.getBlogger());
    }

    @Override
    public List<Blog> findBlogs(long max, int count) {
        return blogCriteria()
                .add(Restrictions.lt("id", max))
                .setMaxResults(count)
                .list();
    }

    private Criteria blogCriteria() {
        return currentSession()
                .createCriteria(Blog.class)
                .addOrder(Order.desc("createTime"));
    }
}
