package com.yamini.moodfood.repository;

import com.yamini.moodfood.model.MealLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog,Long>, JpaSpecificationExecutor<MealLog> {

    List<MealLog> findByMood(String mood);
}
