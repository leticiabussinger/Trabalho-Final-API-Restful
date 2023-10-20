package br.org.serratec.trabalhoApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.trabalhoApi.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	Optional<List<Comentario>> findByPostId(Long id);
}
