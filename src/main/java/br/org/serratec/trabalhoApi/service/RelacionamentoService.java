package br.org.serratec.trabalhoApi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.trabalhoApi.Dtos.RelacionamentoDto;
import br.org.serratec.trabalhoApi.Dtos.RelacionamentoInserirDto;
import br.org.serratec.trabalhoApi.exception.NullException;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.model.Relacionamento;
import br.org.serratec.trabalhoApi.model.Usuario;
import br.org.serratec.trabalhoApi.repository.RelacionamentoRepository;
import br.org.serratec.trabalhoApi.repository.UsuarioRepository;

@Service
public class RelacionamentoService {

	@Autowired
	private RelacionamentoRepository relacionamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public RelacionamentoDto findById(Long idSeguido, Long idSeguidor) {
		
		Optional<Relacionamento> relacionamentoOpt = relacionamentoRepository.findByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(idSeguido, idSeguidor);
		
		if(relacionamentoOpt.isPresent()) {	
			RelacionamentoDto relacionamentoDto = new RelacionamentoDto(relacionamentoOpt.get());
			return relacionamentoDto;
		}
		return null;		
	}
	
	public List<RelacionamentoDto> buscarSeguidoresUser(Long id) {
		
		Optional<List<Relacionamento>> relacionamentos = relacionamentoRepository.findByIdUsuarioSeguidoId(id);
		if(relacionamentos.isPresent()) {
			List<RelacionamentoDto> relacionamentoDto = new ArrayList<>();
			
			for (Relacionamento relacionamento : relacionamentos.get()) {
				relacionamentoDto.add(new RelacionamentoDto(relacionamento));
			}
			return relacionamentoDto;		
		}
		return null;
	}
	
	public List<RelacionamentoDto> buscarSeguindoUser(Long id) {
		
		Optional<List<Relacionamento>> relacionamentos = relacionamentoRepository.findByIdUsuarioSeguidorId(id);
		if(relacionamentos.isPresent()) {
			List<RelacionamentoDto> relacionamentoDto = new ArrayList<>();
			
			for (Relacionamento relacionamento : relacionamentos.get()) {
				relacionamentoDto.add(new RelacionamentoDto(relacionamento));
			}
			return relacionamentoDto;		
		}
		return null;
	}

	public RelacionamentoDto inserir(RelacionamentoInserirDto relacionamentoInserirDto) {
		
		if(relacionamentoInserirDto.getUsuarioSeguido().getId() == null && relacionamentoInserirDto.getUsuarioSeguidor().getId() == null) {
			throw new NullException("O id do usuarioSeguido e do usuarioSeguidor nao podem ser nulos");
			
		} else if(relacionamentoInserirDto.getUsuarioSeguido().getId() == null) {
			throw new NullException("O id do usuarioSeguido nao pode ser nulo");
			
		} else if(relacionamentoInserirDto.getUsuarioSeguidor().getId() == null) {
			throw new NullException("O id do usuarioSeguidor nao pode ser nulo");
		}
		
		Optional<Usuario> userSeguido = usuarioRepository.findById(relacionamentoInserirDto.getUsuarioSeguido().getId());
		Optional<Usuario> userSeguidor = usuarioRepository.findById(relacionamentoInserirDto.getUsuarioSeguidor().getId());
		
		
		if(userSeguido.isPresent() && userSeguidor.isPresent()) {
			if(userSeguido.get().getId() != userSeguidor.get().getId()) {
				Relacionamento relacionamentoInserido = new Relacionamento();
				
				LocalDateTime dataAtual = LocalDateTime.now(); 
				
				relacionamentoInserido.getId().setUsuarioSeguido(relacionamentoInserirDto.getUsuarioSeguido());
				relacionamentoInserido.getId().setUsuarioSeguidor(relacionamentoInserirDto.getUsuarioSeguidor());
				relacionamentoInserido.setDataInicioSeguimento(dataAtual);
				
				relacionamentoInserido = relacionamentoRepository.save(relacionamentoInserido);
				
				RelacionamentoDto relacionamentoDto = new RelacionamentoDto(relacionamentoInserido);
				relacionamentoDto.setUsuario_seguido_nome(userSeguido.get().getNome());
				relacionamentoDto.setUsuario_seguidor_nome(userSeguidor.get().getNome());
				
				return relacionamentoDto;
			}else {
				throw new RecursoNaoEncontradoException("O usuario não pode seguir ele mesmo");
			}
		} else if (userSeguido.isEmpty() && userSeguidor.isEmpty()){
			throw new RecursoNaoEncontradoException("Ambos usuarios não existem");
			
		} else if(userSeguido.isEmpty()) {
			throw new RecursoNaoEncontradoException("O usuario com id " + relacionamentoInserirDto.getUsuarioSeguido().getId() + " não existe");
			
		} else {
			throw new RecursoNaoEncontradoException("O usuario com id " + relacionamentoInserirDto.getUsuarioSeguidor().getId() + " não existe");
			
		}
	}

	public Boolean deletar(Long idSeguido, Long idSeguidor) {
		Optional<Relacionamento> relacionamentoOpt = relacionamentoRepository.findByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(idSeguido, idSeguidor);
	
		if(relacionamentoOpt.isPresent()) {	
			relacionamentoRepository.deleteByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(idSeguido, idSeguidor);
			return true;
		}
		return false;
	}
}
