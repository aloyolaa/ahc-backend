package com.petrotal.ahcbackend.service.security.impl;

import com.petrotal.ahcbackend.entity.AccessHistory;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.repository.AccessHistoryRepository;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessHistoryServiceImpl implements AccessHistoryService {
    private final AccessHistoryRepository accessHistoryRepository;
    private final UserService userService;

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
                String line = String.format("[%s] Usuario %s ha %s.",
                        history.getDateAccess(),
                        history.getUser().getUsername(),
                        history.getAction());
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public void logAccessHistory(String username, String action) {
        User user = username == null ? userService.findByUsername(userService.getUsernameFromSecurityContext()) : userService.findByUsername(username);
        AccessHistory accessHistory = accessHistoryRepository.save(new AccessHistory(user, action));
        log.info("[{}] Usuario {} ha {}.", accessHistory.getDateAccess(), accessHistory.getUser().getUsername(), accessHistory.getAction());
    }
}
