package microblogger.db.jpa;

import microblogger.domain.Blog;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BlogRepositoryImpl implements BlogRepositoryCustom {

    @PersistenceContext(unitName = "blogger")
    private EntityManager em;

    @Override
    public List<Blog> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Blog> findRecent(int count) {
        return em.createQuery("select bl from Blog bl order by bl.createTime desc")
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<Blog> findBlogs(long max, int count) {
        return em.createQuery("select bl from Blog bl where bl.id < :max order by bl.createTime desc")
                .setParameter("max", max)
                .setMaxResults(count)
                .getResultList();
    }
}
