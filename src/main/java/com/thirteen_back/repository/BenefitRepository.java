package com.thirteen_back.repository;

import com.thirteen_back.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    @Query("SELECT b FROM Benefit b WHERE b.benefits < :benefits")
    List<Benefit> findByBenefits(@Param("benefits") String benefits);
}
