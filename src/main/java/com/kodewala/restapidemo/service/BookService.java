package com.kodewala.restapidemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodewala.restapidemo.dto.BookRequest;
import com.kodewala.restapidemo.dto.BookResponse;
import com.kodewala.restapidemo.entity.BookEntity;
import com.kodewala.restapidemo.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public BookResponse createBook(BookRequest request) {

		BookEntity entity = new BookEntity();

		entity.setTitle(request.getTitle());
		entity.setAuthor(request.getAuthor());
		entity.setIsbn(request.getIsbn());
		entity.setPublishedYear(request.getPublishedYear());

		BookEntity saved = bookRepository.save(entity);

		return mapToResponse(saved);
	}

	public List<BookResponse> getAllBooks() {

		List<BookResponse> responses = new ArrayList<>();

		for (BookEntity entity : bookRepository.findAll()) {
			responses.add(mapToResponse(entity));
		}
		return responses;
	}

	public Optional<BookResponse> getBookById(long id) {

		return bookRepository.findById(id).map(this::mapToResponse);
	}

	public Optional<BookResponse> updateBook(long id, BookRequest request) {

		Optional<BookEntity> existing = bookRepository.findById(id);

		if (existing.isEmpty()) {
			return Optional.empty();
		}

		BookEntity entity = existing.get();

		entity.setTitle(request.getTitle());
		entity.setAuthor(request.getAuthor());
		entity.setIsbn(request.getIsbn());
		entity.setPublishedYear(request.getPublishedYear());

		BookEntity updated = bookRepository.save(entity);

		return Optional.of(mapToResponse(updated));
	}

	public boolean deleteBook(long id) {

		if (!bookRepository.existsById(id)) {
			return false;
		}

		bookRepository.deleteById(id);
		return true;
	}

	private BookResponse mapToResponse(BookEntity entity) {

		BookResponse response = new BookResponse();

		response.setId(entity.getId());
		response.setTitle(entity.getTitle());
		response.setAuthor(entity.getAuthor());
		response.setIsbn(entity.getIsbn());
		response.setPublishedYear(entity.getPublishedYear());

		return response;
	}

}
