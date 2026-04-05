package com.example.demo.repository;

import com.example.demo.model.FinancialRecord;
import com.example.demo.model.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    
    Page<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    Page<FinancialRecord> findByCategory(String category, Pageable pageable);
    
    Page<FinancialRecord> findByType(RecordType type, Pageable pageable);

    List<FinancialRecord> findTop5ByOrderByDateDesc();

    @Query("SELECT r.category as category, SUM(r.amount) as total FROM FinancialRecord r WHERE r.type = :type GROUP BY r.category")
    List<Map<String, Object>> findCategoryTotalsByType(RecordType type);
    
    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type = :type")
    java.math.BigDecimal sumAmountByType(RecordType type);
}
