package com.example.Web.domain;


import javax.persistence.*;
import java.io.Serializable;


@Entity
public class BookInstance  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Book book;

    private boolean issued;


    public BookInstance(){
        book = new Book();
    };

    public BookInstance(Book book, boolean issued){
        this.book = book;
        this.issued = issued;
    }

    public Book getBook() {
        return book;
    }

    public Integer getId() {
        return id;
    }

    public boolean getIssued(){
        return  issued;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return "BookInstance(Invent numb: " + id + "  (" + book.toString() + ")issued: " + issued + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BookInstance) {
            if (obj.hashCode() == hashCode()) {
                BookInstance toEq = (BookInstance) obj;
                return book.equals(toEq.book) && (id.equals(toEq.getId())) && (issued == toEq.issued);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return book.hashCode();
    }
}
