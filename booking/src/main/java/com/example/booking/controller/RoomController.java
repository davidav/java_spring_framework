package com.example.booking.controller;

import com.example.booking.dto.mapper.RoomMapper;
import com.example.booking.dto.room.RoomResponse;
import com.example.booking.dto.room.UpsertRoomRequest;
import com.example.booking.entity.Room;

import com.example.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
@Slf4j
public class RoomController {
    private final RoomMapper roomMapper;
    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        log.info("RoomController -> findById {}", id);

        return ResponseEntity.ok(
                roomMapper.roomToResponse(roomService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid UpsertRoomRequest request) {
        log.info("RoomController -> create {}", request.getName());
        Room room = roomService.save(roomMapper.requestToRoom(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roomMapper.roomToResponse(roomService.save(room)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertRoomRequest request) {
        log.info("RoomController -> update id={} request={}", id, request);
        Room room = roomService.update(roomMapper.requestToRoom(id, request));

        return ResponseEntity.ok(roomMapper.roomToResponse(room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("RoomController -> deleteById {}", id);
        roomService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
