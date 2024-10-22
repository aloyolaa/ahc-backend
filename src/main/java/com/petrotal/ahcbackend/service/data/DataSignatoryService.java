package com.petrotal.ahcbackend.service.data;

public interface DataSignatoryService {
    void sign(Long voucherId);

    long countSignatories(Long voucherId);
}
