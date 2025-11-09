package br.csi.politecnico.moviecatalog.service;

import br.csi.politecnico.moviecatalog.dto.FilmeDTO;
import br.csi.politecnico.moviecatalog.exception.NotFoundException;
import br.csi.politecnico.moviecatalog.model.*;
import br.csi.politecnico.moviecatalog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final GeneroRepository generoRepository;
    private final AtorRepository atorRepository;
    private final FilmeGeneroRepository filmeGeneroRepository;
    private final FilmeAtorRepository filmeAtorRepository;

    @Transactional
    public FilmeDTO save(FilmeDTO dto) {
        Filme filme = Filme.builder()
                .titulo(dto.getTitulo())
                .sinopse(dto.getSinopse())
                .anoLancamento(dto.getAnoLancamento())
                .build();

        filme = filmeRepository.save(filme);

        // 1. Relacionamento N:M com Genero
        if (dto.getGenerosIds() != null) {
            Filme finalFilme = filme;
            List<FilmeGenero> filmeGeneros = dto.getGenerosIds().stream()
                    .map(generoId -> {
                        Genero genero = generoRepository.findById(generoId)
                                .orElseThrow(() -> new NotFoundException("Gênero com ID " + generoId + " não encontrado."));
                        return FilmeGenero.builder()
                                .filme(finalFilme)
                                .genero(genero)
                                .build();
                    })
                    .collect(Collectors.toList());
            filmeGeneroRepository.saveAll(filmeGeneros);
            filme.setGeneros(filmeGeneros);
        }

        // 2. Relacionamento N:M com Ator
        if (dto.getAtores() != null) {
            Filme finalFilme1 = filme;
            List<FilmeAtor> filmeAtores = dto.getAtores().stream()
                    .map(filmeAtorDTO -> {
                        Ator ator = atorRepository.findById(filmeAtorDTO.getAtorId())
                                .orElseThrow(() -> new NotFoundException("Ator com ID " + filmeAtorDTO.getAtorId() + " não encontrado."));
                        return FilmeAtor.builder()
                                .filme(finalFilme1)
                                .ator(ator)
                                .papel(filmeAtorDTO.getPapel())
                                .build();
                    })
                    .collect(Collectors.toList());
            filmeAtorRepository.saveAll(filmeAtores);
            filme.setAtores(filmeAtores);
        }

        return toDTO(filme);
    }

    public List<FilmeDTO> findAll() {
        return filmeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FilmeDTO findById(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Filme não encontrado"));
        return toDTO(filme);
    }

    @Transactional
    public FilmeDTO update(Long id, FilmeDTO dto) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Filme não encontrado"));

        filme.setTitulo(dto.getTitulo());
        filme.setSinopse(dto.getSinopse());
        filme.setAnoLancamento(dto.getAnoLancamento());

        // ATUALIZAR GÊNEROS - Modifique a coleção existente
        updateGeneros(filme, dto.getGenerosIds());

        // ATUALIZAR ATORES - Modifique a coleção existente
        updateAtores(filme, dto.getAtores());

        filme = filmeRepository.save(filme);
        return toDTO(filme);
    }

    private void updateGeneros(Filme filme, List<Long> novosGenerosIds) {
        if (novosGenerosIds == null) {
            filme.getGeneros().clear();
            return;
        }

        // Obter IDs atuais
        Set<Long> generosAtuais = filme.getGeneros().stream()
                .map(fg -> fg.getGenero().getId())
                .collect(Collectors.toSet());

        // Remover gêneros que não estão mais na lista
        filme.getGeneros().removeIf(fg -> !novosGenerosIds.contains(fg.getGenero().getId()));

        // Adicionar novos gêneros
        for (Long generoId : novosGenerosIds) {
            if (!generosAtuais.contains(generoId)) {
                Genero genero = generoRepository.findById(generoId)
                        .orElseThrow(() -> new NotFoundException("Gênero com ID " + generoId + " não encontrado."));
                FilmeGenero filmeGenero = FilmeGenero.builder()
                        .filme(filme)
                        .genero(genero)
                        .build();
                filme.getGeneros().add(filmeGenero);
            }
        }
    }

    private void updateAtores(Filme filme, List<FilmeDTO.FilmeAtorDTO> novosAtores) {
        if (novosAtores == null) {
            filme.getAtores().clear();
            return;
        }

        Set<Long> atoresAtuais = filme.getAtores().stream()
                .map(fa -> fa.getAtor().getId())
                .collect(Collectors.toSet());

        filme.getAtores().removeIf(fa -> {
            boolean deveRemover = novosAtores.stream()
                    .noneMatch(novo -> novo.getAtorId().equals(fa.getAtor().getId()));
            return deveRemover;
        });

        for (FilmeDTO.FilmeAtorDTO novoAtor : novosAtores) {
            boolean existe = filme.getAtores().stream()
                    .anyMatch(fa -> fa.getAtor().getId().equals(novoAtor.getAtorId()));

            if (!existe) {
                Ator ator = atorRepository.findById(novoAtor.getAtorId())
                        .orElseThrow(() -> new NotFoundException("Ator com ID " + novoAtor.getAtorId() + " não encontrado."));
                FilmeAtor filmeAtor = FilmeAtor.builder()
                        .filme(filme)
                        .ator(ator)
                        .papel(novoAtor.getPapel())
                        .build();
                filme.getAtores().add(filmeAtor);
            } else {
                filme.getAtores().stream()
                        .filter(fa -> fa.getAtor().getId().equals(novoAtor.getAtorId()))
                        .findFirst()
                        .ifPresent(fa -> fa.setPapel(novoAtor.getPapel()));
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Filme não encontrado"));
        filmeRepository.delete(filme);
    }

    private FilmeDTO toDTO(Filme filme) {
        List<Long> generosIds = filme.getGeneros().stream()
                .map(fg -> fg.getGenero().getId())
                .collect(Collectors.toList());

        List<FilmeDTO.FilmeAtorDTO> atoresDTO = filme.getAtores().stream()
                .map(fa -> FilmeDTO.FilmeAtorDTO.builder()
                        .atorId(fa.getAtor().getId())
                        .papel(fa.getPapel())
                        .build())
                .collect(Collectors.toList());

        return FilmeDTO.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .sinopse(filme.getSinopse())
                .anoLancamento(filme.getAnoLancamento())
                .generosIds(generosIds)
                .atores(atoresDTO)
                .build();
    }
}
