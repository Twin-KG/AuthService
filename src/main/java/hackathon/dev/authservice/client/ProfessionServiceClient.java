package hackathon.dev.authservice.client;

import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.AuthProfessionDto;
import hackathon.dev.authservice.dto.Professions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PROFESSIONSERVICE")
public interface ProfessionServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/professions", consumes = "application/json")
    ZResponse<Professions> getUserByIdOrUsernameOrEmail(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email);

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/professions/mail", consumes = "application/json")
    ZResponse<AuthProfessionDto> getUserByEmail(
            @RequestParam String email);

    @RequestMapping(method = RequestMethod.POST, value = "api/v1/professions", consumes = "application/json")
    ZResponse<AuthProfessionDto> saveProfessions(@RequestBody Professions professions);

}