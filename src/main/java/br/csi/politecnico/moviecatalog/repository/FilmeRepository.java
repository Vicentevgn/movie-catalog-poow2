package br.csi.politecnico.moviecatalog.repository;

import br.csi.politecnico.moviecatalog.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
