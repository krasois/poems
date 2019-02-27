package app.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Indexed
@Table(name = "poems")
public class Poem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    @Field(analyze =  Analyze.YES)
    private String title;

    @Column(name = "author", nullable = false)
    @Field(analyze = Analyze.YES)
    private String author;

    @Column(name = "publish_year", nullable = false)
    private int publishYear;

    @Column(name = "poem_content", nullable = false, length = 20000)
    private String poemContent;

    @Column(name = "date_added", nullable = false)
    private Date dateAdded;

    public Poem() {
    }

    @PrePersist
    protected void onCreate() {
        if (this.dateAdded == null)
            this.setDateAdded(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPoemContent() {
        return poemContent;
    }

    public void setPoemContent(String poemContent) {
        this.poemContent = poemContent;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
