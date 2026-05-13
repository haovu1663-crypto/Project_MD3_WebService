package re.projecwebservice.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                // validateToken() giờ sẽ ném exception thay vì nuốt lỗi
                if (jwtService.validateToken(token)) {
                    String username = jwtService.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            )
                    );
                }

            } catch (ExpiredJwtException e) {
                log.warn("Token hết hạn: {}", e.getMessage());
                // Set attribute → JwtAuthEntryPoint sẽ đọc và trả lỗi chi tiết
                request.setAttribute("jwt_error", "TOKEN_EXPIRED");

            } catch (SignatureException | MalformedJwtException e) {
                log.warn("Token sai chữ ký hoặc định dạng: {}", e.getMessage());
                request.setAttribute("jwt_error", "TOKEN_INVALID");

            } catch (UnsupportedJwtException e) {
                log.warn("Token không được hỗ trợ: {}", e.getMessage());
                request.setAttribute("jwt_error", "TOKEN_UNSUPPORTED");

            } catch (IllegalArgumentException e) {
                log.warn("Token rỗng hoặc null: {}", e.getMessage());
                request.setAttribute("jwt_error", "TOKEN_MISSING");

            } catch (Exception e) {
                log.error("Lỗi xác thực token không xác định: {}", e.getMessage());
                request.setAttribute("jwt_error", "TOKEN_INVALID");
            }
        }

        filterChain.doFilter(request, response);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}