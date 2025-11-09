package br.csi.politecnico.moviecatalog.repository;

import br.csi.politecnico.moviecatalog.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Long> {
}
