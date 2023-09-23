package hackathon.dev.authservice.service.impl;

import hackathon.dev.authservice.model.Tier;
import hackathon.dev.authservice.repo.TierRepo;
import hackathon.dev.authservice.service.TierService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TierServiceImpl implements TierService {

    private final TierRepo tierRepo;

    @Override
    public Tier save(Tier tier) {
        return tierRepo.save(tier);
    }

    public List<Tier> saveAll(List<Tier> tierList) {
        return tierList.stream().map(this::save).toList();
    }

    @Override
    public List<Tier> saveDefaultTiersByProfessionId(Long professionId) {

        Tier tier1 = Tier.builder()
                .imgUrl("https://i.ibb.co/KG8MwXp/octopus.webp").name("Basic").price(2).rank(1).professionId(professionId).build();

        Tier tier2 = Tier.builder()
                .imgUrl("https://i.ibb.co/NrrYpRx/seaslug.webp").name("Classic").price(5).rank(2).professionId(professionId).build();

        Tier tier3 = Tier.builder()
                .imgUrl("https://i.ibb.co/1T4DSNM/tier2.webp").name("Premium").price(7).rank(3).professionId(professionId).build();

        return saveAll(List.of(tier1, tier2, tier3));
    }
}
