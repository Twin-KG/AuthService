package hackathon.dev.authservice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZResponse {
    private boolean success;
    private int code;
    private Object data;
    private String message;
}
