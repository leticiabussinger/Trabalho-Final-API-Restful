package br.org.serratec.trabalhoApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.trabalhoApi.model.Foto;
import br.org.serratec.trabalhoApi.model.Usuario;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	public Optional<Foto> findByUsuario(Usuario usuario);
	
}