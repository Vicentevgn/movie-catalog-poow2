package br.csi.politecnico.moviecatalog.repository;

import br.csi.politecnico.moviecatalog.model.FilmeGenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmeGeneroRepository extends JpaRepository<FilmeGenero, Long> {

//    @Modifying
//    @Query("DELETE FROM FilmeGenero fg WHERE fg.filme.id = :filmeId")
//    void deleteByFilmeId(@Param("filmeId") Long filmeId);

}
