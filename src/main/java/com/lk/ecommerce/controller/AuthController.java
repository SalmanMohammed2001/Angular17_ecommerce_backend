package com.lk.ecommerce.controller;


import com.lk.ecommerce.dto.core.AuthDTO;
import com.lk.ecommerce.dto.core.RefreshTokenDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.dto.core.UserDTO;
import com.lk.ecommerce.entity.RefreshToken;
import com.lk.ecommerce.service.impl.RefreshTokenServiceImpl;
import com.lk.ecommerce.service.impl.UserServiceImpl;
import com.lk.ecommerce.util.JwtUtil;
import com.lk.ecommerce.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final ResponseDTO responseDTO;
    private final RefreshTokenServiceImpl refreshTokenService;

    //constructor injection
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService, RefreshTokenServiceImpl refreshTokenService, ResponseDTO responseDTO) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.responseDTO = responseDTO;
        this.refreshTokenService=refreshTokenService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }

        UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        String refreshToken= refreshTokenService.createRefreshToken(userDTO.getEmail());
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(loadedUser.getEmail());
        authDTO.setToken(token);
        authDTO.setRefreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", authDTO));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenRequest) {
       try {
            RefreshToken refreshToken=refreshTokenService.findByToken(refreshTokenRequest.getToken());
            RefreshToken refreshTokenVerify=refreshTokenService.verifyExpiration(refreshToken);
            String accessToken=jwtUtil.generateToken(
                    new UserDTO(
                            refreshTokenVerify.getUid().getUid(),
                            refreshTokenVerify.getUid().getEmail(),
                            "",
                            "",
                            refreshTokenVerify.getUid().getRole()
                    ));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created,"Success", new RefreshTokenDTO(accessToken,refreshTokenRequest.getToken())));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Refresh token is not in database!", e.getMessage()));
        }

    }




}

