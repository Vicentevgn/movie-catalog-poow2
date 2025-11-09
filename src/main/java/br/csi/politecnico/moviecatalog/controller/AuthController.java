package br.csi.politecnico.moviecatalog.controller;

import br.csi.politecnico.moviecatalog.dto.LoginFormDTO;
import br.csi.politecnico.moviecatalog.dto.ResponseDTO;
import br.csi.politecnico.moviecatalog.dto.UserDTO;
import br.csi.politecnico.moviecatalog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {

    private final AuthService authService;

    @Operation(
        summary = "Registra um novo usuário e retorna o token JWT",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Usuário registrado com sucesso e JWT retornado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Email já cadastrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Dados de entrada inválidos",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserDTO dto) {
        String token = authService.register(dto);
        return new ResponseEntity<>(ResponseDTO.builder().message(token).build(), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Realiza o login e retorna o token JWT",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login realizado com sucesso e JWT retornado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Credenciais inválidas",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Dados de entrada inválidos",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginFormDTO dto) {
        String token = authService.login(dto);
        return ResponseEntity.ok(ResponseDTO.builder().message(token).build());
    }
}
