package hackathon.dev.authservice.controller;

import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.model.Tier;
import hackathon.dev.authservice.service.TierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tiers")
@AllArgsConstructor
public class TierController {

    private final TierService tierService;

    @PostMapping("/default")
    public ResponseEntity<ZResponse<List<Tier>>> createDefaultTiersByProfessionId(
            @RequestParam("professionId") Long professionId
    ){
        List<Tier> resultList = tierService.saveDefaultTiersByProfessionId(professionId);
        return new ResponseEntity(
                ZResponse.<List<Tier>>builder().success(true).code(HttpStatus.CREATED.value()).message("Successfully saved").data(resultList).build(),
                HttpStatus.CREATED
        );
    }
}
