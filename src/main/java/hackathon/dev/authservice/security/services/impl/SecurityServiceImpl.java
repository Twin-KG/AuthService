package hackathon.dev.authservice.security.services.impl;

import feign.FeignException;
import hackathon.dev.authservice.client.ProfessionServiceClient;
import hackathon.dev.authservice.constant.ActiveStatus;
import hackathon.dev.authservice.domain.ZResponse;
import hackathon.dev.authservice.dto.LoginResponseDto;
import hackathon.dev.authservice.dto.LoginUserDto;
import hackathon.dev.authservice.dto.Professions;
import hackathon.dev.authservice.dto.RegisterUserDto;
import hackathon.dev.authservice.exception.domain.EmailAlreadyExistException;
import hackathon.dev.authservice.exception.domain.UserNotFoundException;
import hackathon.dev.authservice.security.services.SecurityService;
import hackathon.dev.authservice.security.utils.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    private final ModelMapper mapper;

    private final ProfessionServiceClient professionServiceClient;

    @Override
    @Transactional
    public LoginResponseDto login(LoginUserDto loginDto) {
        LoginResponseDto response = new LoginResponseDto();

        ZResponse<Professions> professionsZResponse = professionServiceClient
                .getUserByIdOrUsernameOrEmail(null, loginDto.getUsername(), loginDto.getUsername());

        if(professionsZResponse.getData() != null){
            Professions loginUser = professionsZResponse.getData();
            boolean isMatchPwd = passwordEncoder.matches(loginDto.getPassword(), loginUser.getPassword());
            if(isMatchPwd){
                response.setToken(generateAccessToken(loginUser));
                response.setProfessions(loginUser);
                return response;
            }
        }else{
            throw new UserNotFoundException("User is not found");
        }
        return null;
    }

    @Override
    @Transactional
    public Professions register(RegisterUserDto registerUserDto) {

        ZResponse<Professions> user = null;

        try{

            ZResponse<Professions> professionsZResponse = null;

            try{
                professionsZResponse = professionServiceClient
                        .getUserByIdOrUsernameOrEmail(null, null, registerUserDto.getEmail());
            } catch (FeignException e){
                e.printStackTrace();
            }

            if(professionsZResponse != null){
                throw new EmailAlreadyExistException("This email is already registered. Are you trying to login?");
            }

            String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(encodedPassword);

            Professions professions = mapper.map(registerUserDto, Professions.class);
            professions.setActiveStatus(ActiveStatus.ACTIVE);

            user = professionServiceClient.saveProfessions(professions);

        } catch (EmailAlreadyExistException e) {
            throw  e;
        }catch (Exception e){
            e.printStackTrace();
        }


        return user.getData();
    }

    private String generateAccessToken(Professions loginUser) {
        return jwtUtilities.generateToken(loginUser.getUsername(), null);
    }
}
