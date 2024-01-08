package org.ftclub.cabinet.admin.user.service;

import lombok.RequiredArgsConstructor;
import org.ftclub.cabinet.log.LogLevel;
import org.ftclub.cabinet.log.Logging;
import org.ftclub.cabinet.user.domain.LentExtensionType;
import org.ftclub.cabinet.user.domain.User;
import org.ftclub.cabinet.user.service.LentExtensionCommandService;
import org.ftclub.cabinet.user.service.UserQueryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Logging(level = LogLevel.DEBUG)
public class AdminLentExtensionFacadeService {

    private final UserQueryService userQueryService;
    private final LentExtensionCommandService lentExtensionCommandService;

    // TODO : 더 세부적으로 구현해야함
    public void assignLentExtension(String username) {
        LocalDateTime now = LocalDateTime.now();
        User user = userQueryService.getUserByName(username);
        lentExtensionCommandService.createLentExtension(user.getId(), LentExtensionType.ALL, now);
    }
}
