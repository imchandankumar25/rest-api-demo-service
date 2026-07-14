package com.kodewala.restapidemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodewala.restapidemo.dto.BookRequest;
import com.kodewala.restapidemo.dto.BookResponse;
import com.kodewala.restapidemo.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	@PostMapping
	public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {

		BookResponse response = bookService.createBook(request);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<BookResponse>> getAllBooks() {

		List<BookResponse> books = bookService.getAllBooks();

		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable("id") long id) {

		Optional<BookResponse> book = bookService.getBookById(id);

		if (book.isPresent()) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable("id") long id, @RequestBody BookRequest request) {

		Optional<BookResponse> updated = bookService.updateBook(id, request);

		if (updated.isPresent()) {
			return new ResponseEntity<>(updated.get(), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {

		boolean deleted = bookService.deleteBook(id);

		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
