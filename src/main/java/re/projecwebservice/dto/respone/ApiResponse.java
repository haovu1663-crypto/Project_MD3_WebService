package re.projecwebservice.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;
    private String error;
    private LocalDateTime timestamp;

    public ApiResponse(String message, String status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
