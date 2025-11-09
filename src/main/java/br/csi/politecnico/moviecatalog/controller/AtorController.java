package br.csi.politecnico.moviecatalog.controller;

import br.csi.politecnico.moviecatalog.dto.AtorDTO;
import br.csi.politecnico.moviecatalog.dto.ResponseDTO;
import br.csi.politecnico.moviecatalog.service.AtorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/atores")
@RequiredArgsConstructor
@Tag(name = "Atores", description = "Endpoints para gerenciamento de Atores de filmes")
@SecurityRequirement(name = "bearerAuth")
public class AtorController {

    private final AtorService atorService;

    @Operation(
        summary = "Cria um novo ator",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Ator criado com sucesso",
                content = @Content(schema = @Schema(implementation = AtorDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: nome do ator em branco)",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<AtorDTO>> create(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo ator") @RequestBody AtorDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Criado!", atorService.save(dto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Lista todos os atores",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de atores retornada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = AtorDTO.class)))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<List<AtorDTO>>> findAll() {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Atores", atorService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Busca um ator por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ator encontrado",
                content = @Content(schema = @Schema(implementation = AtorDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Ator não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<AtorDTO>> findById(@Parameter(description = "ID do ator a ser buscado") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Ator", atorService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Atualiza um ator existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ator atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = AtorDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Ator não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<AtorDTO>> update(@Parameter(description = "ID do ator a ser atualizado") @PathVariable Long id, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do ator para atualização") @RequestBody AtorDTO dto) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Ator atualizado!", atorService.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Deleta um ator por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ator deletado com sucesso",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Ator não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@Parameter(description = "ID do ator a ser deletado") @PathVariable Long id) {
        try {
            atorService.delete(id);
            return ResponseEntity.ok(ResponseDTO.builder().message("Ator deletado com sucesso").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }


}
