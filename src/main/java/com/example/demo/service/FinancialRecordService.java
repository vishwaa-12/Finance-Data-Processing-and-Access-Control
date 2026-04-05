package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.FinancialRecord;
import com.example.demo.model.RecordType;
import com.example.demo.model.User;
import com.example.demo.repository.FinancialRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.Objects;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    public FinancialRecordService(FinancialRecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    public Page<FinancialRecord> getAllRecords(String category, RecordType type, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Objects.requireNonNull(pageable, "Pageable must not be null");
        if (startDate != null && endDate != null) {
            return recordRepository.findByDateBetween(startDate, endDate, pageable);
        } else if (category != null) {
            return recordRepository.findByCategory(category, pageable);
        } else if (type != null) {
            return recordRepository.findByType(type, pageable);
        }
        return recordRepository.findAll(pageable);
    }

    public FinancialRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Financial record not found with id: " + id));
    }

    @org.springframework.cache.annotation.CacheEvict(value = "dashboardSummary", allEntries = true)
    public FinancialRecord createRecord(FinancialRecord record, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        record.setCreatedBy(user);
        return recordRepository.save(record);
    }

    @org.springframework.cache.annotation.CacheEvict(value = "dashboardSummary", allEntries = true)
    public FinancialRecord updateRecord(Long id, FinancialRecord recordDetails) {
        FinancialRecord record = getRecordById(id);
        
        record.setAmount(recordDetails.getAmount());
        record.setType(recordDetails.getType());
        record.setCategory(recordDetails.getCategory());
        record.setDate(recordDetails.getDate());
        record.setNotes(recordDetails.getNotes());
        
        return recordRepository.save(record);
    }

    @org.springframework.cache.annotation.CacheEvict(value = "dashboardSummary", allEntries = true)
    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordById(id);
        recordRepository.delete(record);
    }
}
