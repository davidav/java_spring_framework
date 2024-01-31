package com.example.booking.dto.mapper;

import com.example.booking.dto.room.RoomResponse;
import com.example.booking.dto.room.UpsertRoomRequest;
import com.example.booking.entity.Room;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    RoomResponse roomToResponse(Room room);

    Room requestToRoom(UpsertRoomRequest request);

    Room requestToRoom(Long id, UpsertRoomRequest request);

}
