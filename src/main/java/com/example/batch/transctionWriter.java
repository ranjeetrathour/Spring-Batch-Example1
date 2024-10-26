package com.example.batch;

import com.example.modle.Transaction;
import com.example.repo.TrabsactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class transctionWriter implements ItemWriter<Transaction> {

    private final TrabsactionRepository repository;

    @Override
    public void write(Chunk<? extends Transaction> chunk) throws Exception {
        repository.saveAllAndFlush(chunk);
    }
}
