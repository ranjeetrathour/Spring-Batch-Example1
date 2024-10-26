package com.example.batch;

import com.example.modle.Transaction;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransctionReader {

    @Bean
    public FlatFileItemReader<Transaction> transactionItemReader() {
        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();

        // Set the input file location
        reader.setResource(new ClassPathResource("data.csv"));
        reader.setLinesToSkip(1);

        // Set custom separator for CSV file in case of blank lines
        reader.setRecordSeparatorPolicy(new SimpleRecordSeparatorPolicy());

        // Define the line mapper that will map lines to Transaction objects
        reader.setLineMapper(new DefaultLineMapper<Transaction>() {{
            // Create and configure tokenizer
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(",");
                setNames("accountNumber", "amount", "transactionDate");
            }});

            // Create and configure field set mapper
            setFieldSetMapper(new CustomFieldSetMapper());
        }});

        return reader;
    }

    private static class CustomFieldSetMapper implements FieldSetMapper<Transaction> {
        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public Transaction mapFieldSet(FieldSet fieldSet) {
            Transaction transaction = new Transaction();

            // Log values for debugging
            System.out.println("Reading accountNumber: " + fieldSet.readString("accountNumber"));
            System.out.println("Reading amount: " + fieldSet.readString("amount")); // Check what is being read
            System.out.println("Reading transactionDate: " + fieldSet.readString("transactionDate"));

            transaction.setAccountNumber(fieldSet.readString("accountNumber"));
            transaction.setAmount(fieldSet.readFloat("amount"));
            LocalDate localDate = LocalDate.parse(fieldSet.readString("transactionDate"), dateTimeFormatter);
            transaction.setTransactionDate(localDate.atStartOfDay()); // Convert LocalDate to LocalDateTime

            return transaction;
        }
    }
}
