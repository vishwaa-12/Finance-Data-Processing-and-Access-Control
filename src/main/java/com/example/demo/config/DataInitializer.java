package com.example.demo.config;

import com.example.demo.model.RecordType;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.FinancialRecord;
import com.example.demo.repository.FinancialRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, 
                                      FinancialRecordRepository recordRepository, 
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ADMIN);
                User analyst = new User("analyst", passwordEncoder.encode("analyst123"), Role.ANALYST);
                User viewer = new User("viewer", passwordEncoder.encode("viewer123"), Role.VIEWER);

                userRepository.saveAll(Arrays.asList(admin, analyst, viewer));

                FinancialRecord r1 = new FinancialRecord();
                r1.setAmount(new BigDecimal("5000.00"));
                r1.setType(RecordType.INCOME);
                r1.setCategory("Salary");
                r1.setDate(LocalDate.now().minusDays(5));
                r1.setNotes("Monthly salary");
                r1.setCreatedBy(admin);

                FinancialRecord r2 = new FinancialRecord();
                r2.setAmount(new BigDecimal("150.50"));
                r2.setType(RecordType.EXPENSE);
                r2.setCategory("Groceries");
                r2.setDate(LocalDate.now().minusDays(2));
                r2.setNotes("Weekly groceries");
                r2.setCreatedBy(admin);

                FinancialRecord r3 = new FinancialRecord();
                r3.setAmount(new BigDecimal("60.00"));
                r3.setType(RecordType.EXPENSE);
                r3.setCategory("Utilities");
                r3.setDate(LocalDate.now().minusDays(1));
                r3.setNotes("Internet bill");
                r3.setCreatedBy(admin);

                recordRepository.saveAll(Arrays.asList(r1, r2, r3));
            }
        };
    }
}
