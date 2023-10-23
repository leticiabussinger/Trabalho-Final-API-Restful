package br.org.serratec.trabalhoApi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.trabalhoApi.Dtos.ComentarioAtualizarDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioInserirDto;
import br.org.serratec.trabalhoApi.exception.NullException;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.model.Comentario;
import br.org.serratec.trabalhoApi.model.Post;
import br.org.serratec.trabalhoApi.model.Relacionamento;
import br.org.serratec.trabalhoApi.model.Usuario;
import br.org.serratec.trabalhoApi.repository.ComentarioRepository;
import br.org.serratec.trabalhoApi.repository.PostRepository;
import br.org.serratec.trabalhoApi.repository.RelacionamentoRepository;
import br.org.serratec.trabalhoApi.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RelacionamentoRepository relacionamentoRepository;

	public List<ComentarioDto> findAll() {
		List<Comentario> comentarios = comentarioRepository.findAll();

		List<ComentarioDto> comentariosDTO = new ArrayList<>();

		if(comentarios != null) {
			for (Comentario comentario : comentarios) {
				comentariosDTO.add(new ComentarioDto(comentario));
			}
			return comentariosDTO;		
		}
		return null;
	}

	public ComentarioDto findById(Long id) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		if (comentarioOpt.isEmpty()) {
			return null;
		}

		ComentarioDto comentarioDto = new ComentarioDto(comentarioOpt.get());

		return comentarioDto;
	}

	public List<ComentarioDto> buscarComentarioPost(Long id) {

		Optional<List<Comentario>> comentarios = comentarioRepository.findByPostId(id);

		if (comentarios.isPresent()) {
			List<ComentarioDto> comentarioDto = new ArrayList<>();

			for (Comentario comentario : comentarios.get()) {
				comentarioDto.add(new ComentarioDto(comentario));
			}
			return comentarioDto;
		}
		return null;
	}

	public ComentarioDto inserir(ComentarioInserirDto comentarioInserirDto) {
		
		if(comentarioInserirDto.getUsuario().getId() == null && comentarioInserirDto.getPost().getId() == null) {
			throw new NullException("O id do usuario e do post nao podem ser nulos");
			
		} else if(comentarioInserirDto.getUsuario().getId() == null) {
			throw new NullException("O id do usuario nao pode ser nulo");
			
		} else if(comentarioInserirDto.getPost().getId() == null) {
			throw new NullException("O id do post nao pode ser nulo");
		}
		
		Optional<Post> postOpt = postRepository.findById(comentarioInserirDto.getPost().getId());
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(comentarioInserirDto.getUsuario().getId());

		if(postOpt.isPresent() && usuarioOpt.isPresent()) {
			
			Long idUsuarioPost = postOpt.get().getUsuario().getId();
			
			if(comentarioInserirDto.getUsuario().getId() != idUsuarioPost) {
				Optional<Relacionamento> relacionamentoOpt = relacionamentoRepository.findByIdUsuarioSeguidoIdAndIdUsuarioSeguidorId(idUsuarioPost, usuarioOpt.get().getId());
				
				if(relacionamentoOpt.isEmpty()) {
					throw new RecursoNaoEncontradoException("Não foi possivel inserir um comentario, pois o usuario de id " + usuarioOpt.get().getId() + " não está seguindo o usuario criador do post");
				}				
			}
			
			Comentario comentarioInserido = new Comentario();
		
			LocalDateTime dataAtual = LocalDateTime.now();
			
			comentarioInserido.setTexto(comentarioInserirDto.getTexto());
			comentarioInserido.setDataCriacao(dataAtual);
			comentarioInserido.setPost(postOpt.get());
			comentarioInserido.setUsuario(usuarioOpt.get());
			
			comentarioInserido = comentarioRepository.save(comentarioInserido);
			
			ComentarioDto comentarioDto = new ComentarioDto(comentarioInserido);
			
			return comentarioDto;
		}
		
		return null;
	}

	public Boolean deletar(Long id) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		if (comentarioOpt.isPresent()) {
			comentarioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public ComentarioDto atualizar(Long id, ComentarioAtualizarDto comentarioAtualizarDto) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		
		Comentario comentario = new Comentario();
		
		if (comentarioOpt.isPresent()) {
			comentario.setId(id);
			comentario.setDataCriacao(comentarioOpt.get().getDataCriacao());
			comentario.setTexto(comentarioAtualizarDto.getTexto());
			comentario.setPost(comentarioOpt.get().getPost());
			comentario.setUsuario(comentarioOpt.get().getUsuario());
			comentario = comentarioRepository.save(comentario);

			ComentarioDto comentarioDto = new ComentarioDto(comentario);

			return comentarioDto;
		}
		return null;
	}
}
