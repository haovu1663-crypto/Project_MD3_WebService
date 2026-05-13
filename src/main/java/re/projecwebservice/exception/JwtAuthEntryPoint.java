package re.projecwebservice.exception;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import re.projecwebservice.dto.respone.ApiResponse;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Bắt tất cả lỗi 401 từ Spring Security và trả về JSON có cấu trúc rõ ràng.
 * Attribute "jwt_error" được set bởi JwtAuthFilter khi phát hiện token lỗi cụ thể.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Lấy thông báo lỗi chi tiết do JwtAuthFilter đặt vào request attribute
        String jwtError = (String) request.getAttribute("jwt_error");

        String message;
        String errorDetail;

        if (jwtError != null) {
            // Lỗi cụ thể từ JWT
            switch (jwtError) {
                case "TOKEN_EXPIRED" -> {
                    message = "Token đã hết hạn, vui lòng đăng nhập lại";
                    errorDetail = "JWT token has expired";
                }
                case "TOKEN_INVALID" -> {
                    message = "Token không hợp lệ";
                    errorDetail = "JWT token is invalid or malformed";
                }
                case "TOKEN_MISSING" -> {
                    message = "Không tìm thấy token, vui lòng đăng nhập";
                    errorDetail = "Authorization header is missing or does not start with Bearer";
                }
                case "TOKEN_UNSUPPORTED" -> {
                    message = "Token không được hỗ trợ";
                    errorDetail = "JWT token is unsupported";
                }
                default -> {
                    message = "Xác thực thất bại";
                    errorDetail = jwtError;
                }
            }
        } else {
            // Không có token (truy cập endpoint bảo vệ mà không gửi token)
            message = "xác thực quền thất bại ";
            errorDetail = authException.getMessage();
        }

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                message,
                "401 Unauthorized",
                null,
                errorDetail,
                LocalDateTime.now()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(response.getWriter(), apiResponse);
    }
}
