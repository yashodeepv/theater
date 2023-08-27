package com.yasho.solution.repo;

import com.yasho.solution.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    List<Movie> findAllByTitle(String title);
    Optional<Movie> findByTitle(String title);
}
