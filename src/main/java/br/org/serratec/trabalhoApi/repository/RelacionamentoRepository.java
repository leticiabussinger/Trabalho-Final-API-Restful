package br.org.serratec.trabalhoApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.trabalhoApi.model.Relacionamento;

public interface RelacionamentoRepository extends JpaRepository<Relacionamento, Long> {

	Optional<List<Relacionamento>> findByIdUsuarioSeguidoId(Long id);
	Optional<List<Relacionamento>> findByIdUsuarioSeguidorId(Long id);
	Optional<Relacionamento> findByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(Long idSeguido, Long idSeguidor);
	Optional<Relacionamento> deleteByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(Long idSeguido, Long idSeguidor);
}
