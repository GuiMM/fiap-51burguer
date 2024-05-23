package com.fiap.burguer.service;

import com.fiap.burguer.entities.Status;
import com.fiap.burguer.repository.StatusRepository;

public class StatusService {
    private final StatusRepository StatusRepository;

    public StatusService(StatusRepository StatusRepository) {
        this.StatusRepository = StatusRepository;
    }

    public Status saveStatusOrUpdate(Status Status) {
        return StatusRepository.save(Status);
    }

    public Status findById(int id) {
        return StatusRepository.findById(id);
    }

    public void deleteById(int id) {
        StatusRepository.deleteById(id);
    }
}
