package br.csi.politecnico.moviecatalog.service;

import br.csi.politecnico.moviecatalog.dto.AtorDTO;
import br.csi.politecnico.moviecatalog.exception.NotFoundException;
import br.csi.politecnico.moviecatalog.model.Ator;
import br.csi.politecnico.moviecatalog.repository.AtorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtorService {

    private final AtorRepository atorRepository;

    public AtorDTO save(AtorDTO dto) {
        Ator ator = Ator.builder()
                .nome(dto.getNome())
                .biografia(dto.getBiografia())
                .build();
        ator = atorRepository.save(ator);
        return toDTO(ator);
    }

    public List<AtorDTO> findAll() {
        List<Ator> atores = atorRepository.findAll();
        if (atores.isEmpty()) {
            return Collections.emptyList();
        }
        return atores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AtorDTO findById(Long id) {
        Ator ator = atorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ator não encontrado"));
        return toDTO(ator);
    }

    public AtorDTO update(Long id, AtorDTO dto) {
        Ator ator = atorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ator não encontrado"));

        ator.setNome(dto.getNome());
        ator.setBiografia(dto.getBiografia());
        ator = atorRepository.save(ator);
        return toDTO(ator);
    }

    public void delete(Long id) {
        if (!atorRepository.existsById(id)) {
            throw new NotFoundException("Ator não encontrado");
        }
        atorRepository.deleteById(id);
    }

    private AtorDTO toDTO(Ator ator) {
        return AtorDTO.builder()
                .id(ator.getId())
                .nome(ator.getNome())
                .biografia(ator.getBiografia())
                .build();
    }
}
