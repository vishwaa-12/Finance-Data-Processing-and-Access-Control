package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import com.example.demo.model.FinancialRecord;

public class DashboardSummaryDto {
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;
    private Map<String, BigDecimal> incomeByCategory;
    private Map<String, BigDecimal> expensesByCategory;
    private List<FinancialRecord> recentActivity;

    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }

    public BigDecimal getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(BigDecimal totalExpenses) { this.totalExpenses = totalExpenses; }

    public BigDecimal getNetBalance() { return netBalance; }
    public void setNetBalance(BigDecimal netBalance) { this.netBalance = netBalance; }

    public Map<String, BigDecimal> getIncomeByCategory() { return incomeByCategory; }
    public void setIncomeByCategory(Map<String, BigDecimal> incomeByCategory) { this.incomeByCategory = incomeByCategory; }

    public Map<String, BigDecimal> getExpensesByCategory() { return expensesByCategory; }
    public void setExpensesByCategory(Map<String, BigDecimal> expensesByCategory) { this.expensesByCategory = expensesByCategory; }

    public List<FinancialRecord> getRecentActivity() { return recentActivity; }
    public void setRecentActivity(List<FinancialRecord> recentActivity) { this.recentActivity = recentActivity; }
}
