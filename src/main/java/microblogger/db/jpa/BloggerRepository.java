package microblogger.db.jpa;

import microblogger.domain.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloggerRepository extends JpaRepository<Blogger, Long>, BloggerSweeper {
    Blogger findByUsername(String username);
}
