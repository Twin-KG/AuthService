package hackathon.dev.authservice.service;

import hackathon.dev.authservice.model.Tier;

import java.util.List;

public interface TierService {
    Tier save(Tier tier);
    List<Tier> saveDefaultTiersByProfessionId(Long professionId);
}
