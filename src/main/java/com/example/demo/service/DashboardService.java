package com.example.demo.service;

import com.example.demo.dto.DashboardSummaryDto;
import com.example.demo.model.RecordType;
import com.example.demo.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public DashboardService(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @org.springframework.cache.annotation.Cacheable(value = "dashboardSummary")
    public DashboardSummaryDto getDashboardSummary() {
        DashboardSummaryDto summary = new DashboardSummaryDto();

        BigDecimal totalIncome = recordRepository.sumAmountByType(RecordType.INCOME);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        
        BigDecimal totalExpenses = recordRepository.sumAmountByType(RecordType.EXPENSE);
        if (totalExpenses == null) totalExpenses = BigDecimal.ZERO;

        summary.setTotalIncome(totalIncome);
        summary.setTotalExpenses(totalExpenses);
        summary.setNetBalance(totalIncome.subtract(totalExpenses));

        // Get Category Totals
        List<Map<String, Object>> incomeData = recordRepository.findCategoryTotalsByType(RecordType.INCOME);
        summary.setIncomeByCategory(convertListToMap(incomeData));

        List<Map<String, Object>> expenseData = recordRepository.findCategoryTotalsByType(RecordType.EXPENSE);
        summary.setExpensesByCategory(convertListToMap(expenseData));

        // Recent Activity (we just get all records, sort and take top 5 in memory for simplicity)
        // A better approach would be a custom query with OrderBy and Pageable
        summary.setRecentActivity(recordRepository.findTop5ByOrderByDateDesc());

        return summary;
    }

    private Map<String, BigDecimal> convertListToMap(List<Map<String, Object>> list) {
        Map<String, BigDecimal> map = new HashMap<>();
        for (Map<String, Object> item : list) {
            String category = (String) item.get("category");
            BigDecimal total;
            Object val = item.get("total");
            if (val instanceof BigDecimal) {
                total = (BigDecimal) val;
            } else if (val instanceof Double) {
                total = BigDecimal.valueOf((Double) val);
            } else if (val instanceof Long) {
                 total = BigDecimal.valueOf((Long) val);
            } else {
                 total = new BigDecimal(val.toString());
            }
            map.put(category, total);
        }
        return map;
    }
}
