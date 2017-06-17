package microblogger.db.hibernate;

import microblogger.db.BloggerRepository;
import microblogger.domain.Blogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;

@Repository
@Primary
@Transactional
public class HibernateBloggerRepository implements BloggerRepository {

    private SessionFactory sessionFactory;

    @Inject
    public HibernateBloggerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Blogger save(Blogger blogger) {
        Serializable id = currentSession().save(blogger);

        return new Blogger((Long)id,
                blogger.getUsername(),
                blogger.getPassword(),
                blogger.getFirstName(),
                blogger.getLastName(),
                blogger.getEmail());
    }

    @Override
    public Blogger findByUsername(String username) {
        return (Blogger)currentSession()
                .createCriteria(Blogger.class)
                .add(Restrictions.eq("username", username))
                .list().get(0);
    }
}
