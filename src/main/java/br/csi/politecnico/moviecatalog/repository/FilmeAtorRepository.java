package br.csi.politecnico.moviecatalog.repository;

import br.csi.politecnico.moviecatalog.model.FilmeAtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FilmeAtorRepository extends JpaRepository<FilmeAtor, Long> {

//    @Modifying
//    @Query("DELETE FROM FilmeAtor fa WHERE fa.filme.id = :filmeId")
//    void deleteByFilmeId(@Param("filmeId") Long filmeId);

}
