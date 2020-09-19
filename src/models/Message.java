package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "messages")
@NamedQueries({
    @NamedQuery(
            name = "getAllMessages",
            query = "SELECT m FROM Message AS m ORDER BY m.id DESC"
            ),
    @NamedQuery(
            name = "getMessagesCount",
            query = "SELECT COUNT(m) FROM Message AS m"
            ),
    @NamedQuery(
            name = "getMyAllMessages",
            query = "SELECT m FROM Message AS m WHERE m.teacher = :teacher ORDER BY m.id DESC"
            ),
    @NamedQuery(
            name = "getMyMessagesCount",
            query = "SELECT COUNT(m) FROM Message AS m WHERE m.teacher = :teacher"
            ),
    @NamedQuery(
            name = "getAllMessagesForStudents",
            query = "SELECT m FROM Message AS m WHERE m.open_range = 0 ORDER BY m.id DESC"
            ),
    @NamedQuery(
            name = "getAllMessagesForStudentsCount",
            query = "SELECT COUNT(m) FROM Message AS m WHERE m.open_range = 0"
            )
})

@Entity
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "message_date", nullable = false)
    private Date message_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "open_range", nullable = false)
    private Integer open_range;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Date getMessage_date() {
        return message_date;
    }

    public void setMessage_date(Date message_date) {
        this.message_date = message_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getOpen_range() {
        return open_range;
    }

    public void setOpen_range(Integer open_range) {
        this.open_range = open_range;
    }
}
