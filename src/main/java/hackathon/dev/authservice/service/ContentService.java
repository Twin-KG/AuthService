package hackathon.dev.authservice.service;

import hackathon.dev.authservice.dto.ContentRequestDto;
import hackathon.dev.authservice.model.Content;
import org.springframework.data.domain.Page;

public interface ContentService {
    Page<Content> getAllContentWithPaging(int page, int size);
    Page<Content> getAllContentsByAccessTypeAndTierId(int page, int size, Long accessType, Long tierId);
    Content saveContent(ContentRequestDto contentRequestDto);
    Content updateContentById(Long id, ContentRequestDto contentRequestDto);
    Content deleteContentById(Long id);
}
