package com.example.batch;

import com.example.modle.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {
    @Override
    public Transaction process(Transaction item) throws Exception {
        // Check if the amount is divisible by 3
        if (item.getAmount().floatValue()%3==0) {
           return null;
        }else {
            item.setAmount(item.getAmount()+2);
        }
        return item; // Return the processed (or unchanged) item
    }
}
