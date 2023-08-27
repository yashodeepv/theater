package com.yasho.solution.repo;

import com.yasho.solution.entity.Movie;
import com.yasho.solution.entity.Offer;
import com.yasho.solution.entity.Showtime;
import com.yasho.solution.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {


}
