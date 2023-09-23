package hackathon.dev.authservice.security.services.impl;

import feign.FeignException;
import hackathon.dev.authservice.client.ProfessionServiceClient;
import hackathon.dev.authservice.constant.ActiveStatus;
import hackathon.dev.authservice.constant.CustomMessage;
import hackathon.dev.authservice.constant.QueueConfig;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    private final ModelMapper mapper;

    private final ProfessionServiceClient professionServiceClient;
    private final RabbitTemplate template;

    @Override
    @Transactional
    public LoginResponseDto login(LoginUserDto loginDto) {
        LoginResponseDto response = new LoginResponseDto();

        ZResponse<Professions> professionsZResponse = professionServiceClient
                .getUserByEmail(loginDto.getUsername());

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
                        .getUserByEmail(registerUserDto.getEmail());
            } catch (FeignException e){
                //
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

        Professions professions = user.getData();

        // Send to message queue
        CustomMessage message = new CustomMessage();
        message.setMessageId(UUID.randomUUID().toString());
        message.setEmail(professions.getEmail());
        message.setUsername(professions.getUsername());
        message.setAccountCreatedDate(new Date());
        template.convertAndSend(QueueConfig.EXCHANGE,
                QueueConfig.ROUTING_KEY, message);

        return professions;
    }

    private String generateAccessToken(Professions loginUser) {
        return jwtUtilities.generateToken(loginUser.getUsername(), null);
    }
}
