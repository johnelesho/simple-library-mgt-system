package tech.elsoft.maidcclibrarymanagementsystem.logging;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;
import tech.elsoft.maidcclibrarymanagementsystem.logging.dto.LogMessage;

import java.io.IOException;

/**
 * According to: https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl
 */
@Slf4j
public class LoggableDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);
        try {
            super.doDispatch(request, response);
        } finally {
            if(!request.getPathInfo().contains("/swagger") && !request.getPathInfo().contains("/v3/api-docs")) {
                log(request, response, handler);
            }
                updateResponse(response);

        }
    }

    private void log(HttpServletRequest request, HttpServletResponse response, HandlerExecutionChain handler) {
        var msg = buildLogMessage(request, response, handler);
        log.info(String.valueOf(msg));
    }

    private LogMessage buildLogMessage(HttpServletRequest request, HttpServletResponse response, HandlerExecutionChain handler) {
        var msg = new LogMessage();
        msg.setHttpStatus(response.getStatus());
        msg.setHttpMethod(request.getMethod());
        msg.setPath(request.getRequestURI());
        msg.setClientIp(request.getRemoteAddr());
        msg.setJavaMethod(handler.toString());
        msg.setResponse(getResponsePayload(response));
        return msg;
    }

    @SneakyThrows
    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                return new String(buf, 0, length, wrapper.getCharacterEncoding());
            }
        }
        return "[unknown]";
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        var responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }
}
