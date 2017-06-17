package microblogger.db.jpa;

import microblogger.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom {

    Blog findByBlogger(long id);

    Blog findById(long id);
}
