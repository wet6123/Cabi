package org.ftclub.cabinet.cabinet.controller;

import lombok.RequiredArgsConstructor;
import org.ftclub.cabinet.auth.AuthGuard;
import org.ftclub.cabinet.auth.AuthGuard.Level;
import org.ftclub.cabinet.cabinet.service.CabinetServiceImpl;
import org.ftclub.cabinet.dto.CabinetDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RestController
public class CabinetController {

    private final CabinetServiceImpl cabinetService;

    @GetMapping("/cabinet/{cabinetId}")
    @AuthGuard(level = Level.USER_OR_ADMIN)
    public CabinetDto getCabinetById(
            @PathVariable Long cabinetId) {
        return cabinetService.getCabinetById(cabinetId);
    }
}
