package com.petrotal.ahcbackend.service.data;

public interface DataSignatoryService {
    void sign(Long voucherId, String username);

    long countSignatories(Long voucherId);
}
