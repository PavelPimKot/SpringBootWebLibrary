package com.example.Web.repos;

import com.example.Web.domain.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BookRepository extends CrudRepository<Book, Integer> {


    List<Book> findByOrderByAuthorsAsc();

    List<Book> findByOrderByTitleAsc();

    List<Book> findByOrderByPublishingYearAsc();

    List<Book> findByOrderByPagesNumberAsc();


    List<Book> findByAuthorsOrTitleContainingIgnoreCase(String authors, String title);

    List<Book> findByAuthorsAndTitleAndPagesNumberAndPublishingYear(String authors, String title, Integer pagesNumber, Integer publishingYear );

}
