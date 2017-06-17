package microblogger.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BloggerRepositoryImpl implements BloggerSweeper {

    @PersistenceContext(unitName = "blogger")
    private EntityManager em;

    public int eliteSweep() {
        String update =
            "UPDATE Blogger blogger " +
                    "SET blogger.status = 'Elite' " +
                    "WHERE blogger.status = 'Newbie' " +
                    "AND blogger.id IN (" +
                    "SELECT b FROM Blogger b WHERE (" +
                    "  SELECT COUNT(bl) FROM Blog bl where bl.blogger = b.id) > 10000" +
                    ")";
        return em.createQuery(update).executeUpdate();
    }
}
