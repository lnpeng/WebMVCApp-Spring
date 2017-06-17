package microblogger.db;

import microblogger.domain.Blogger;

public interface BloggerRepository {
    Blogger save(Blogger bloger);

    Blogger findByUsername(String username);
}
