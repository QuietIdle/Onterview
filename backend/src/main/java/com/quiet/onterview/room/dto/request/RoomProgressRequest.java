package com.quiet.onterview.room.dto.request;

import com.quiet.onterview.room.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomProgressRequest {
    RoomStatus type;
    Integer index;
}
