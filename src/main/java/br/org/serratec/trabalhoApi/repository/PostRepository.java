package br.org.serratec.trabalhoApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.trabalhoApi.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<List<Post>> findByUsuarioId(Long id); 
}
