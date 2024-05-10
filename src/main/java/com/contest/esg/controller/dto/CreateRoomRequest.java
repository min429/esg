package com.contest.esg.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRoomRequest {
    private Long hostId;
    private Long guestId;
    private String roomName;
}
