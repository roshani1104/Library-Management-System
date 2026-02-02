package com.library.model;

public class Book {

	private int bookId;
	private String title;
	private String author;
	private String category;
	private int publishedYear;
	private int totalCopies;
	private int availableCopies;

	public Book() {
	}

	// Constructor for INSERT
	public Book(String title, String author, String category, int publishedYear, int totalCopies, int availableCopies) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.publishedYear = publishedYear;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}

	// Constructor for SELECT
	public Book(int bookId, String title, String author, String category, int publishedYear, int totalCopies,
			int availableCopies) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.category = category;
		this.publishedYear = publishedYear;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}

	// Getters & Setters
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}
}
