package hackathon.dev.authservice.service.impl;

import hackathon.dev.authservice.client.ProfessionServiceClient;
import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.ContentRequestDto;
import hackathon.dev.authservice.dto.Professions;
import hackathon.dev.authservice.model.AccessType;
import hackathon.dev.authservice.model.Content;
import hackathon.dev.authservice.model.Tier;
import hackathon.dev.authservice.repo.AccessTypeRepo;
import hackathon.dev.authservice.repo.ContentRepo;
import hackathon.dev.authservice.repo.TierRepo;
import hackathon.dev.authservice.service.ContentService;
import hackathon.dev.authservice.utils.FileUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepo contentRepo;
    private final AccessTypeRepo accessTypeRepo;
    private final TierRepo tierRepo;

    @Override
    public Page<Content> getAllContentWithPaging(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return contentRepo.findAll(pageRequest);
    }

    @Override
    public Page<Content> getAllContentsByAccessTypeAndTierId(int page, int size, Long accessTypeId, Long tierId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return contentRepo.findContentByAccessTypeOrTierId(pageRequest, accessTypeId, tierId);
    }

    @Override
    public Content saveContent(ContentRequestDto dto) {

        Long currentTime = System.currentTimeMillis();

        String audioPath = FileUtils.save(dto.getAudio(), "/audio/" + dto.getProfessionalId() + "/" + currentTime);
        String imagePath = FileUtils.save(dto.getImage(), "/image/" + dto.getProfessionalId() + "/" + currentTime);
        String videoPath = FileUtils.save(dto.getVideo(), "/video/" + dto.getProfessionalId() + "/" + currentTime);

        Optional<AccessType> accessType = accessTypeRepo.findById(dto.getAccessTypeId());
        Optional<Tier> tier = tierRepo.findById(dto.getTierId());

        Content content = Content.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .embedUrl(dto.getEmbedUrl())
                .link(dto.getLink())
                .professionalId(dto.getProfessionalId())
                .audio(audioPath)
                .image(imagePath)
                .video(videoPath)
                .tier(tier.isPresent() ? tier.get(): null)
                .accessType(accessType.isPresent() ? accessType.get(): null)
                .professionalId(dto.getProfessionalId())
                .build();

        return contentRepo.save(content);
    }

    @Override
    public Content updateContentById(Long id, ContentRequestDto contentRequestDto) {
        return null;
    }

    @Override
    public Content deleteContentById(Long id) {
        return null;
    }
}
