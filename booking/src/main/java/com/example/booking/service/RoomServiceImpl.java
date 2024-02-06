package com.example.booking.service;

import com.example.booking.entity.Room;
import com.example.booking.repo.RoomRepository;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Room with id {} not found", id).getMessage()));
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Room room) {
        Room existedRoom = findById(room.getId());
        AppHelperUtils.copyNonNullProperties(room, existedRoom);
        return roomRepository.save(existedRoom);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

}
