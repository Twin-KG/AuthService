package hackathon.dev.authservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class ContentRequestDto {
    private String title;
    private String content;
    private MultipartFile audio;
    private MultipartFile video;
    private MultipartFile image;
    private String link;
    private String embedUrl;
    private String status;

    private Long professionalId;
    private Long tierId;
    private Long accessTypeId;
}
