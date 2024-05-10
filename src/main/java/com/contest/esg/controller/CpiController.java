package com.contest.esg.controller;

import com.contest.esg.config.CommonResponse;
import com.contest.esg.domain.Cpi;
import com.contest.esg.domain.Post;
import com.contest.esg.service.CpiService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cpi")
@CrossOrigin(origins = "*")
public class CpiController {

    private final CpiService cpiService;

    @GetMapping("/{registrationNumber}")
    public CommonResponse<List<Cpi>> findAllByRegistrationNumber(@Parameter(name = "registrationNumber", description = "사업자등록번호", in = ParameterIn.PATH) @PathVariable("registrationNumber") String registrationNumber) {
        return CommonResponse.success(cpiService.findAllByRegistrationNumber(registrationNumber));
    }
}
