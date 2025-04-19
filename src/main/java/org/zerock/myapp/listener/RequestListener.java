package org.zerock.myapp.listener;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.SpringBootVersion;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2

// Class contains `required fields`, You have to `force` NoArgsConstructor. (***)
@NoArgsConstructor

@WebListener
public final class RequestListener implements ServletRequestListener {


    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        String url = request.getRequestURL().toString();
//        if(url.contains("favicon.ico")) return;

        String requestId;

        try {
            Method getRequestIdMethod = request.getClass().getMethod("getRequestId");
            Objects.requireNonNull(getRequestIdMethod);
            request.setCharacterEncoding("UTF-8");
            
            requestId = (String) getRequestIdMethod.invoke(request);
        } catch (Exception e) {
            requestId = SpringBootVersion.getVersion();
        } // try-catch

        boolean requestedSessionIdValid = request.isRequestedSessionIdValid();
        String requestedSessionId = request.getRequestedSessionId();

        String format = "* Request Initialized (RID: %s, SID: %s, %s).".formatted(requestId, requestedSessionId, requestedSessionIdValid);

        log.debug("");
        log.debug("****************************************************************************");
        log.debug(format);
        log.debug("****************************************************************************");

        String method = request.getMethod();
        Map<String, String[]> parameterMap = request.getParameterMap();

        log.info("1. Method: {}, URL: {}", method, url);
        log.info("2. ParameterMap: {}", parameterMap);
    } // requestInitialized

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

//        String url = request.getRequestURL().toString();
//        if(url.contains("favicon.ico")) return;

        String requestId;

        try {
            Method getRequestIdMethod = request.getClass().getMethod("getRequestId");
            Objects.requireNonNull(getRequestIdMethod);

            requestId = (String) getRequestIdMethod.invoke(request);
        } catch (Exception e) {
            requestId = SpringBootVersion.getVersion();
        } // try-catch

        boolean requestedSessionIdValid = request.isRequestedSessionIdValid();
        String requestedSessionId = request.getRequestedSessionId();

        String format = "* Request Destroyed (RID: %s, SID: %s, %s).".formatted(requestId, requestedSessionId, requestedSessionIdValid);

        log.debug("****************************************************************************");
        log.debug(format);
        log.debug("****************************************************************************");
    } // requestDestroyed

} // end class
