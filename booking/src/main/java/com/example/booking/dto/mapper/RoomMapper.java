package com.example.booking.dto.mapper;

import com.example.booking.dto.room.RoomResponse;
import com.example.booking.dto.room.RoomRequest;
import com.example.booking.entity.Room;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    RoomResponse roomToResponse(Room room);

    Room requestToRoom(RoomRequest request);

    Room requestToRoom(Long id, RoomRequest request);

}
