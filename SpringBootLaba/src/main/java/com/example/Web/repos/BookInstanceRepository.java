package com.example.Web.repos;

import com.example.Web.domain.Book;
import com.example.Web.domain.BookInstance;
import org.springframework.data.repository.CrudRepository;


public interface BookInstanceRepository extends CrudRepository<BookInstance, Integer> {
}
