package jp.co.idolFunSite.app.controller.common;

import jp.co.idolFunSite.app.dto.common.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 正常系レスポンスを共通フォーマットに統一します。
 */
@ControllerAdvice
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body instanceof ApiResponse<?>) {
            return body;
        }

        String requestId = null;
        if (request instanceof ServletServerHttpRequest servletRequest) {
            Object attribute = servletRequest.getServletRequest().getAttribute(RequestIdFilter.REQUEST_ID_ATTRIBUTE);
            requestId = attribute instanceof String value ? value : null;
        }

        return ApiResponse.success(body, requestId);
    }
}
