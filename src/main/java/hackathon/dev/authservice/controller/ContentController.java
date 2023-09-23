package hackathon.dev.authservice.controller;

import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.ContentRequestDto;
import hackathon.dev.authservice.model.Content;
import hackathon.dev.authservice.service.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contents")
@AllArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public ResponseEntity<ZResponse<Content>> addNewContent(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam("video") MultipartFile video,
            @RequestParam("image") MultipartFile image,
            @RequestParam("link") String link,
            @RequestParam("embeddedUrl") String embeddedUrl,
            @RequestParam("status") String status,
            @RequestParam("accessTypeId") Long accessTypeId,
            @RequestParam("tierId") Long tierId,
            @RequestParam("professionalId") Long professionalId){

        ContentRequestDto dto = ContentRequestDto
                .builder()
                .title(title).content(content).audio(audio).video(video).image(image)
                .link(link).embedUrl(embeddedUrl).status(status)
                .tierId(tierId).accessTypeId(accessTypeId).professionalId(professionalId)
                .build();

        Content result = contentService.saveContent(dto);
        return new ResponseEntity(
                ZResponse.<Content>builder()
                        .data(result).message("Successfully saved content").code(HttpStatus.CREATED.value()).success(true).build(),
                        HttpStatus.CREATED);
    }

    @GetMapping
    public Page<Content> getContentsByAccessTypeAndTierId(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "accessTypeId") Long accessTypeId,
            @RequestParam(value = "tierId", required = false) Long tierId){
        return contentService.getAllContentsByAccessTypeAndTierId(page, size, accessTypeId, tierId);
    }

}
