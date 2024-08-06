package com.thirteen_back.repository;

import com.thirteen_back.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.name = :name")
    List<Card> findByName(@Param("name") String name);
    List<Card> findAllByOrderByAnnualFeeAsc();

    @Query("SELECT c FROM Card c WHERE c.performance < :performance")
    List<Card> findByPerformance(@Param("performance") String performance);
}
