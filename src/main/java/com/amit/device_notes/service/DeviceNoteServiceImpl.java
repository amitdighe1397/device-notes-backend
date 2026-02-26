package com.amit.device_notes.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amit.device_notes.dto.CreateDeviceNoteRequest;
import com.amit.device_notes.dto.DeviceNoteResponse;
import com.amit.device_notes.entity.DeviceNote;
import com.amit.device_notes.repository.DeviceNoteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceNoteServiceImpl implements DeviceNoteService {

	private final DeviceNoteRepository repository;

	@Override
	@Transactional
	public DeviceNoteResponse createNote(Long deviceId, String user, CreateDeviceNoteRequest request) {

		validateCreateRequest(user, request);

		DeviceNote entity = DeviceNote.builder().deviceId(deviceId).note(request.getNote()).createdBy(user).build();

		DeviceNote saved = repository.save(entity);

		log.info("Note created successfully id={} deviceId={}", saved.getId(), deviceId);

		return mapToResponse(saved);
	}

	@Override
	public List<DeviceNoteResponse> getNotes(Long deviceId, Integer limit) {

		int validatedLimit = validateLimit(limit);

		List<DeviceNote> notes = repository.findByDeviceIdOrderByCreatedAtDesc(deviceId,
				PageRequest.of(0, validatedLimit));

		return notes.stream().map(this::mapToResponse).toList();
	}

	// ========================
	// Validation Methods
	// ========================

	private void validateCreateRequest(String user, CreateDeviceNoteRequest request) {

		if (user == null || user.isBlank()) {
			log.warn("Missing X-User header");
			throw new IllegalArgumentException("X-User header is required");
		}

		if (request == null || request.getNote() == null || request.getNote().isBlank()) {
			log.warn("Invalid note content");
			throw new IllegalArgumentException("Note must not be blank");
		}

		if (request.getNote().length() > 1000) {
			log.warn("Note exceeds max length");
			throw new IllegalArgumentException("Note must not exceed 1000 characters");
		}
	}

	private int validateLimit(Integer limit) {

		int effectiveLimit = (limit == null) ? 20 : limit;

		if (effectiveLimit < 1 || effectiveLimit > 100) {
			log.warn("Invalid limit value: {}", limit);
			throw new IllegalArgumentException("Limit must be between 1 and 100");
		}

		return effectiveLimit;
	}

	private DeviceNoteResponse mapToResponse(DeviceNote entity) {
		return DeviceNoteResponse.builder().id(entity.getId()).deviceId(entity.getDeviceId()).note(entity.getNote())
				.createdBy(entity.getCreatedBy()).createdAt(entity.getCreatedAt()).build();
	}
}