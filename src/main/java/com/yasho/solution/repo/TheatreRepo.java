package com.yasho.solution.repo;

import com.yasho.solution.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TheatreRepo extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByName(String name);
}
