package microblogger.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="message")
    private String message;

    @Column(name="created_at")
    private Date createTime;

    @ManyToOne
    @JoinColumn(name="blogger")
    private Blogger blogger;

    private Blog() {}

    public Blog(String message, Date time, Blogger blogger) {
        this(null, message, time, blogger);
    }

    public Blog(Long id, String message, Date time, Blogger blogger) {
        this.id = id;
        this.message = message;
        this.createTime = time;
        this.blogger = blogger;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Blogger getBlogger() {
        return blogger;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "id", "createTime");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id", "createTime");
    }
}
