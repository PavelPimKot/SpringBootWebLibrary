package com.example.Web;


import com.example.Web.domain.Book;
import com.example.Web.domain.BookInstance;
import com.example.Web.repos.BookInstanceRepository;
import com.example.Web.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @RequestMapping(value = {"/book/{id}"}, method = RequestMethod.GET)
    public String showBook(@PathVariable Integer id, Map<String, Object> model) {
        model.put("CurrentBook", bookRepository.findById(id).get());
        return "book";
    }

    @RequestMapping(value = {"/bookInstance/{id}"}, method = RequestMethod.GET)
    public String showBookInst(@PathVariable Integer id, Map<String, Object> model) {
        model.put("CurrentBookInst", bookInstanceRepository.findById(id).get());
        return "bookInstance";
    }

    @RequestMapping(value = {"home"}, method = RequestMethod.GET)
    public String showHome() {
        return "home";
    }

    @RequestMapping(value = {"addBookInst"}, method = RequestMethod.GET)
    public String saveBookInst(@ModelAttribute("bookInstance") BookInstance bookInstance) {
        if (!(bookInstance.getBook().getAuthors() == null || bookInstance.getBook().getTitle() == null)) {
            Book book = bookInstance.getBook();
            BookInstance toSave;
            if (bookRepository.
                    findByAuthorsAndTitleAndPagesNumberAndPublishingYear
                            (book.getAuthors(), book.getTitle(), book.getPagesNumber(), book.getPublishingYear()).isEmpty()) {
                toSave = new BookInstance(book, bookInstance.getIssued());
            } else {

                toSave = new BookInstance(bookRepository.
                        findByAuthorsAndTitleAndPagesNumberAndPublishingYear
                                (book.getAuthors(), book.getTitle(), book.getPagesNumber(), book.getPublishingYear()).get(0), bookInstance.getIssued());
            }
            bookInstanceRepository.save(toSave);
        }
        return "addBookInst";
    }

    @RequestMapping(value = {"help"}, method = RequestMethod.GET)
    public String  showHelp() {
        return "help";
    }

    @RequestMapping(value = {"search"}, method = RequestMethod.GET)
    public String search(@RequestParam(required = false) String searchRes, Map<String, Object> model) {
        if (searchRes != null) {
            Iterable<Book> booksSearch = bookRepository.findByAuthorsOrTitleContainingIgnoreCase(searchRes, searchRes);
            model.put("booksSearch", booksSearch);
        }
        return "search";
    }

    @RequestMapping(value = {"book/changeBook"}, method = RequestMethod.GET)
    public String changeBook(Map<String, Object> model, @ModelAttribute("book") Book book) {
        Book bok = bookRepository.findById(book.getId()).get();
        bok.setPagesNumber(book.getPagesNumber());
        bok.setPublishingYear(book.getPublishingYear());
        bok.setTitle(book.getTitle());
        bok.setAuthors(book.getAuthors());
        bookRepository.save(bok);
        model.put("CurrentBook", bok);
        return "book";
    }

    @RequestMapping(value = {"sortTable"}, method = RequestMethod.GET)
    public String sortTable(Map<String, Object> model,@RequestParam(value = "select") String value){
        Iterable<Book> books = null;
        switch (value){
            case "Author":
                books = bookRepository.findByOrderByAuthorsAsc();
                break;
            case "Title":
                books = bookRepository.findByOrderByTitleAsc();
                break;
            case "PagesNumber":
                books = bookRepository.findByOrderByPagesNumberAsc();
                break;
            case "PublishingYear":
                books = bookRepository.findByOrderByPublishingYearAsc();
                break;
        }
        model.put("books", books);
        return "bookTable";
    }


    @RequestMapping(value = {"bookInstance/changeBookInstance"}, method = RequestMethod.GET)
    public String changeBookInst(Map<String, Object> model, @RequestParam(value = "id") Integer id, @RequestParam(value = "issued") String issued) {
        BookInstance bookInstance = bookInstanceRepository.findById(id).get();
        if (issued.equals("true")) {
            bookInstance.setIssued(true);
        } else {
            bookInstance.setIssued(false);
        }
        bookInstanceRepository.save(bookInstance);
        model.put("CurrentBookInst", bookInstance);
        return "bookInstance";
    }


    @RequestMapping(value = {"book/deleteBook"}, method = RequestMethod.GET)
    public String deleteBook(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
        bookRepository.deleteById(id);
        return "redirect:/bookTable";
    }


    @RequestMapping(value = {"bookInstance/deleteBookInstance"}, method = RequestMethod.GET)
    public String deleteBookInst(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
        BookInstance bookInstance = bookInstanceRepository.findById(id).get();
        bookInstanceRepository.deleteById(id);
        return "redirect:/book/"+bookInstance.getBook().getId();
    }


    @RequestMapping(value = {"bookTable"}, method = RequestMethod.GET)
    public String outBook(Map<String, Object> model) {
        Iterable<Book> books = bookRepository.findAll();
        model.put("books", books);
        return "bookTable";
    }


    @RequestMapping(value = {"addBook"}, method = RequestMethod.GET)
    public String saveBook(@ModelAttribute("book") Book book) {
        if (!(book.getAuthors() == null || book.getTitle() == null)) {
            Book toSave = new Book(book.getAuthors(), book.getTitle(), book.getPublishingYear(), book.getPagesNumber());
            bookRepository.save(toSave);
            return "redirect:/bookTable";
        }
        return "addBook";
    }

}