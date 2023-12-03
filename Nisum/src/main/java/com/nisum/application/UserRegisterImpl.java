package com.nisum.application;

import com.nisum.application.dto.ApiResponse;
import com.nisum.application.dto.PhonesDTO;
import com.nisum.application.dto.UserRequest;
import com.nisum.application.dto.UserResponse;
import com.nisum.application.exceptions.CustomException;
import com.nisum.domain.UserPhonesRepository;
import com.nisum.domain.UsersRepository;
import com.nisum.domain.model.UserPhones;
import com.nisum.domain.model.Users;
import com.nisum.domain.utilities.Utils;
import com.nisum.infrastructure.config.MessageProvider;
import com.nisum.infrastructure.config.jwt.JwtGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class UserRegisterImpl implements UserRegister {

    @Value("${app.dateFormat}")
    private String formatDate;

    @Value("${app.email-pattern}")
    private String emailPattern;

    @Value("${app.password-pattern}")
    private String passwordPattern;
    private final Utils utils;

    private final UserPhonesRepository userPhonesRepository;
    private final UsersRepository usersRepository;
    private final MessageProvider<String> messageProvider;

    private final JwtGenerator jwtGenerator;

    public UserRegisterImpl(MessageProvider<String> messageProvider, Utils utils, UserPhonesRepository userPhonesRepository, UsersRepository usersRepository, MessageProvider<String> messageProvider1, JwtGenerator jwtGenerator) {
        this.utils = utils;
        this.userPhonesRepository = userPhonesRepository;
        this.usersRepository = usersRepository;
        this.messageProvider = messageProvider1;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> userCreate(UserRequest request) {
        try {
            if (!validarEmail(request.getEmail())) {
                throw new CustomException(messageProvider.getMessage("nisum.validate.email"), HttpStatus.BAD_REQUEST);
            }

            if (!validarPassword(request.getPassword())) {
                throw new CustomException(messageProvider.getMessage("nisum.validate.password"), HttpStatus.BAD_REQUEST);
            }

            if (usuarioYaRegistrado(request.getEmail())) {
                throw new CustomException(messageProvider.getMessage("nisum.validate.email.exist"), HttpStatus.CONFLICT);
            }

            Users usr = crearUsuario(request);
            guardarTelefonos(request.getPhones(), usr);

            return crearRespuestaUsuario(usr);

        } catch (CustomException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private boolean validarEmail(String email) {
        return utils.validarExpresionRegular(email, emailPattern);
    }

    private boolean validarPassword(String password) {
        return utils.validarExpresionRegular(password, passwordPattern);
    }

    private boolean usuarioYaRegistrado(String email) {
        return usersRepository.findByEmail(email) != null;
    }

    private Users crearUsuario(UserRequest request) {
        Date dt = new Date();
        Users usr = new Users();
        String idUser = UUID.randomUUID().toString();
        usr.setCreated(dt);
        usr.setId(idUser);
        usr.setEmail(request.getEmail());
        usr.setModified(dt);
        usr.setName(request.getName());
        usr.setIsactive(true);
        usr.setPassword(request.getPassword());
        usr.setToken(jwtGenerator.generateToken(request.getEmail()));
        usr.setModified(dt);
        usr.setLastLogin(dt);

        return usersRepository.save(usr);
    }

    private void guardarTelefonos(List<PhonesDTO> phones, Users usr) {
        try {
            Optional.ofNullable(phones)
                    .ifPresent(phoneList ->
                            phoneList.forEach(ph -> {
                                UserPhones phone = new UserPhones();
                                phone.setIdUser(usr);
                                phone.setId(userPhonesRepository.getNextValId());
                                phone.setNumber(ph.getNumber());
                                phone.setCityCode(ph.getCitycode());
                                phone.setContryCode(ph.getContrycode());
                                userPhonesRepository.save(phone);
                            })
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CustomException(messageProvider.getMessage("nisum.log.error.create.phones"), HttpStatus.CONFLICT);
        }
    }

    private ResponseEntity<ApiResponse<Object>> crearRespuestaUsuario(Users usr) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder().
                data(UserResponse.builder()
                        .id(usr.getId())
                        .created(utils.formatDate(usr.getCreated(), formatDate))
                        .modified(null)
                        .token(usr.getToken())
                        .last_login(utils.formatDate(usr.getLastLogin(), formatDate))
                        .isActive(usr.getIsactive())
                        .build())
                .build());
    }
}
