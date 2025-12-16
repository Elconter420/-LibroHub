package edu.univalle.librohub.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.univalle.librohub.catalog.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> { }
