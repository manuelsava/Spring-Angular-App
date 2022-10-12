package com.manuelsava.demo.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manuelsava.demo.borrow.Borrow;
import com.manuelsava.demo.enrollment.Enrollment;
import com.manuelsava.demo.university.University;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Book")
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "serial")
    private Long id;

    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    @NotNull
    @NotBlank
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "author", columnDefinition = "TEXT", nullable = false)
    @NotNull
    private String author;

    @Transient
    private Integer avaliableCopies;

    @Column(name = "pages", columnDefinition = "INT", nullable = false)
    private Integer pages;

    @Column(name = "copies", columnDefinition = "INT", nullable = false)
    @NotNull
    private Integer copies;

    @ManyToOne
    @JsonIgnore
    @NotNull
    @JoinColumn(
            name = "university_id",
            foreignKey = @ForeignKey(
                    name = "book_university_id_fk"
            )
    )
    private University university;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Borrow> borrows = new HashSet<>();

    @Transient
    private Boolean isBorrowed;

    public Book(String title, String description, String author, Integer pages, Integer copies, University university) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.pages = pages;
        this.university = university;
    }

    public Book(String title, String description, String author, Integer pages, Integer copies) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.pages = pages;
        this.copies = copies;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPages() {
        return pages;
    }

    public Integer getCopies() {
        return copies;
    }

    public Integer getAvaliableCopies() {
        return copies - borrows.size();
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public University getUniversity() {
        return university;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Boolean getBorrowed() {
        return borrows.size() > 0;
    }

    public void addBorrow(Borrow borrow){
        if(!borrows.contains(borrow)){
            borrows.add(borrow);
        }
    }

    public void removeBorrow(Borrow borrow){
        if(borrows.contains(borrow)){
            borrows.remove(borrow);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", avaliableCopies=" + avaliableCopies +
                ", pages=" + pages +
                ", copies=" + copies +
                ", university=" + university +
                ", borrows=" + borrows +
                ", isBorrowed=" + isBorrowed +
                '}';
    }
}
