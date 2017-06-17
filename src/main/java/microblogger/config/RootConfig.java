package microblogger.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ImportResource("classpath:META-INF/spring/message.xml")
@ComponentScan(basePackages = {"microblogger"},
    excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = "microblogger.web.*"),
        @Filter(type = FilterType.REGEX, pattern = "microblogger.api.*"),
        @Filter(type = FilterType.REGEX, pattern = "microblogger.db.jdbc.*"),
        @Filter(type = FilterType.REGEX, pattern = "microblogger.db.hibernate.*"),
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = {DataConfig.class})
    })
public class RootConfig {
}
