package com.example.Web.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//@Entity — Указывает, что данный бин (класс) является сущностью.
@Entity
public class Book implements Serializable {

    //@Id — id колонки
    //@GeneratedValue — указывает, что данное свойство будет создаваться согласно указанной стратегии.
    //( в моем случае id генерируется автоматически)
    //Если мы используем тип генерации по умолчанию,
    // поставщик сохраняемости будет определять значения на основе типа атрибута первичного ключа.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@OneToMany — указывает на связь один ко многим. Применяется с другой стороны от сущности с @ManyToOne
    //mappedBy - в каждой Экземляре будет столбец для id книги к которой он принадлежит
    //  fetchType - eager - при загрузке книги будут так же загружаться все ее экземпляры
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book",cascade = CascadeType.ALL)
    private Set<BookInstance> bookInstance;

    private String authors;

    private String title;

    private int publishingYear;

    private int pagesNumber;

    public Book() {
    }

    ;

    public Book(String authors, String title, int publishingYear, int pagesNumber) {
        this.authors = authors;
        this.title = title;
        this.pagesNumber = pagesNumber;
        this.publishingYear = publishingYear;
    }

    public Book(Integer id, String authors, String title, int publishingYear, int pagesNumber) {
        this.id = id;
        this.authors = authors;
        this.title = title;
        this.pagesNumber = pagesNumber;
        this.publishingYear = publishingYear;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBookInstance(Set<BookInstance> bookInstance) {
        this.bookInstance = bookInstance;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public void addBookInstance(BookInstance bookInstance) {
        this.bookInstance.add(bookInstance);
    }

    public Integer getId() {
        return id;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }


    public int getPublishingYear() {
        return publishingYear;
    }

    public String getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public Set<BookInstance> getBookInstance() {
        return bookInstance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Book) {
            if (obj.hashCode() == hashCode()) {
                Book toEq = (Book) obj;
                return authors.equals(toEq.authors) && title.equals(toEq.title) && (publishingYear == toEq.publishingYear)
                        && (pagesNumber == toEq.pagesNumber);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Book(Authors: " + authors + " Title: " + title + " Pub. Year: " + publishingYear +
                " Number of pages: " + pagesNumber + ")";
    }

    @Override
    public int hashCode() {
        return authors.hashCode() ^ title.hashCode() ^ pagesNumber ^ publishingYear;
    }


}