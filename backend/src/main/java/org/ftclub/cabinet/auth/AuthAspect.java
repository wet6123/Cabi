package org.ftclub.cabinet.auth;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.ftclub.cabinet.config.JwtProperties;
import org.ftclub.cabinet.exception.ExceptionStatus;
import org.ftclub.cabinet.exception.ServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final TokenValidator tokenValidator;

    private final JwtProperties jwtProperties;

    @Before(value = "@annotation(authGuard)")
    public void AuthToken(AuthGuard authGuard) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        switch (authGuard.level()) {
            case ADMIN_ONLY:
                if (tokenValidator.isTokenValid(request, jwtProperties.getAdminTokenName())
                        == false) {
                    throw new ServiceException(ExceptionStatus.UNAUTHORIZED);
                }
                break;
            case USER_ONLY:
                if (tokenValidator.isTokenValid(request, jwtProperties.getMainTokenName())
                        == false) {
                    throw new ServiceException(ExceptionStatus.UNAUTHORIZED);
                }
                break;
            case USER_OR_ADMIN:
                if (tokenValidator.isTokenValid(request, jwtProperties.getAdminTokenName()) == false
                        && tokenValidator.isTokenValid(request, jwtProperties.getMainTokenName())
                        == false) {
                    throw new ServiceException(ExceptionStatus.UNAUTHORIZED);
                }
                break;
        }
    }
}
