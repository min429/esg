package com.contest.esg.controller;

import com.contest.esg.config.CommonResponse;
import com.contest.esg.controller.dto.CreateRoomRequest;
import com.contest.esg.controller.dto.ExitRequest;
import com.contest.esg.domain.ChatMessage;
import com.contest.esg.domain.ChatRoom;
import com.contest.esg.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public CommonResponse<ChatRoom> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        return CommonResponse.success(chatRoomService.createRoom(createRoomRequest));
    }

    @GetMapping("/all")
    public CommonResponse<List<ChatRoom>> findAllRoom() {
        return CommonResponse.success(chatRoomService.findAllRoom());
    }

    @GetMapping("/all/{id}")
    public CommonResponse<List<ChatRoom>> findAllRoomsByUserId(@Parameter(name = "id", description = "유저 고유 식별 번호(숫자)", in = ParameterIn.PATH) @PathVariable("id") Long userId) {
        return CommonResponse.success(chatRoomService.findAllRoomsByUserId(userId));
    }

    @GetMapping("/enter/{id}")
    public CommonResponse<List<ChatMessage>> enter(@Parameter(name = "id", description = "채팅방 고유 식별 번호(문자)", in = ParameterIn.PATH) @PathVariable("id") String roomId) {
        return CommonResponse.success(chatRoomService.findChatMessages(roomId));
    }

    @PostMapping("/exit")
    public CommonResponse<String> exit(@RequestBody ExitRequest exitRequest) {
        chatRoomService.exit(exitRequest);
        return CommonResponse.success();
    }
}