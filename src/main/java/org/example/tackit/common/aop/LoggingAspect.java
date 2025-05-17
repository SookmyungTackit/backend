package org.example.tackit.common.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.tackit.domain.Free_board.Free_post.repository.FreeMemberJPARepository;
import org.example.tackit.domain.admin.repository.UserLogRepository;
import org.example.tackit.domain.entity.Member;
import org.example.tackit.domain.entity.Role;
import org.example.tackit.domain.entity.UserLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final UserLogRepository userLogRepository;
    private final FreeMemberJPARepository freeMemberJPARepository;

    @Around("execution(* org.example.tackit..*Controller.*(..))")
    public Object logUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        long start = System.currentTimeMillis();
        Object result = null;
        // 기본 정보
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();

        // 사용자 정보
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : "anonymous";

        Role role = null;
        String org = null;
        if (!"anonymous".equals(email)) {
            Member member = freeMemberJPARepository.findByEmail(email).orElse(null);
            if (member != null) {
                role = member.getRole();
                org = member.getOrganization();
            }
        }

        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            long execTime = System.currentTimeMillis() - start;

            // 자동으로 유추한 action/resource
            String[] uriParts = uri.split("/");
            String resource = uriParts.length > 1 ? uriParts[1] : "unknown";
            String action = method.toLowerCase() + "_" + (uriParts.length > 2 ? uriParts[2] : "default");

            // 로그저장
            UserLog userLog = UserLog.builder()
                    .role(role)
                    .memberId(email)
                    .organization(org)
                    .ipAddress(ip)
                    .memberAgent(userAgent)
                    .timestamp(LocalDateTime.now())
                    .executionTime(execTime)
                    .requestUri(uri)
                    .action(action)
                    .resource(resource)
                    .build();

            userLogRepository.save(userLog);

            log.info("User [{}] performed [{}] on [{}] in {}ms from [{}]",
                    email, action, uri, execTime, ip);
        }
    }
}
