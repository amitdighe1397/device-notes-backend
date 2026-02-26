package com.amit.device_notes.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.device_notes.entity.DeviceNote;

public interface DeviceNoteRepository extends JpaRepository<DeviceNote, Long> {

	List<DeviceNote> findByDeviceIdOrderByCreatedAtDesc(Long deviceId, Pageable pageable);
}