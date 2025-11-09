package br.csi.politecnico.moviecatalog.service;

import br.csi.politecnico.moviecatalog.dto.GeneroDTO;
import br.csi.politecnico.moviecatalog.exception.NotFoundException;
import br.csi.politecnico.moviecatalog.model.Genero;
import br.csi.politecnico.moviecatalog.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroDTO save(GeneroDTO dto) {
        Genero genero = Genero.builder()
                .nome(dto.getNome())
                .build();
        genero = generoRepository.save(genero);
        return toDTO(genero);
    }

    public List<GeneroDTO> findAll() {
        return generoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GeneroDTO findById(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gênero não encontrado"));
        return toDTO(genero);
    }

    public GeneroDTO update(Long id, GeneroDTO dto) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gênero não encontrado"));

        genero.setNome(dto.getNome());
        genero = generoRepository.save(genero);
        return toDTO(genero);
    }

    public void delete(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new NotFoundException("Gênero não encontrado");
        }
        generoRepository.deleteById(id);
    }

    private GeneroDTO toDTO(Genero genero) {
        return GeneroDTO.builder()
                .id(genero.getId())
                .nome(genero.getNome())
                .build();
    }
}
