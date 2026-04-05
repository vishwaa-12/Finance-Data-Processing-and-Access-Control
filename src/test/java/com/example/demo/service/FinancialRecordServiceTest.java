package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.FinancialRecord;
import com.example.demo.model.RecordType;
import com.example.demo.repository.FinancialRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancialRecordServiceTest {

    @Mock
    private FinancialRecordRepository recordRepository;

    @InjectMocks
    private FinancialRecordService recordService;

    private FinancialRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new FinancialRecord();
        testRecord.setId(1L);
        testRecord.setAmount(new BigDecimal("100.00"));
        testRecord.setType(RecordType.INCOME);
        testRecord.setCategory("Salary");
    }

    /**
     * UNIT TESTING / WHITE BOX TESTING
     * This explicitly tests the internal logic branching and behavior of the Service logic 
     * by artificially manipulating the internal mock data objects.
     */
    @Test
    void testGetRecordById_Success() {
        // Arrange
        when(recordRepository.findById(1L)).thenReturn(Optional.of(testRecord));

        // Act
        FinancialRecord result = recordService.getRecordById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("100.00"), result.getAmount());
        assertEquals("Salary", result.getCategory());
        verify(recordRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRecordById_NotFound_ThrowsException() {
        // Arrange
        when(recordRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            recordService.getRecordById(99L);
        });

        assertTrue(exception.getMessage().contains("Financial record not found"));
    }
}
