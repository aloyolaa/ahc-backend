package com.petrotal.ahcbackend.service.security.impl;

import com.petrotal.ahcbackend.entity.AccessHistory;
import com.petrotal.ahcbackend.repository.AccessHistoryRepository;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessHistoryServiceImpl implements AccessHistoryService {
    private final AccessHistoryRepository accessHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AccessHistory> getAll() {
        return accessHistoryRepository.findByOrderByDateAccessDesc();
    }

    @Override
    @Transactional
    public void save(AccessHistory accessHistory) {
        accessHistoryRepository.save(accessHistory);
    }

    @Override
    public String getAccessHistory(List<AccessHistory> histories) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            for (AccessHistory history : histories) {
                String line = String.format("[%s] Usuario %s - ID: %d",
                        history.getDateAccess(),
                        history.getUser().getUsername(),
                        history.getUser().getId());
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
