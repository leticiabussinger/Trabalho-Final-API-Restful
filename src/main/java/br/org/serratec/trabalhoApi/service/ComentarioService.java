package br.org.serratec.trabalhoApi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.trabalhoApi.Dtos.ComentarioDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioInserirDto;
import br.org.serratec.trabalhoApi.model.Comentario;
import br.org.serratec.trabalhoApi.model.Post;
import br.org.serratec.trabalhoApi.repository.ComentarioRepository;
import br.org.serratec.trabalhoApi.repository.PostRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private PostRepository postRepository;

	public List<ComentarioDto> findAll() {
		List<Comentario> comentarios = comentarioRepository.findAll();

		List<ComentarioDto> comentariosDTO = new ArrayList<>();

		for (Comentario comentario : comentarios) {
			comentariosDTO.add(new ComentarioDto(comentario));
		}
		return comentariosDTO;
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
		
		Optional<Post> post = postRepository.findById(comentarioInserirDto.getPost().getId());

		if(post.isPresent()) {
			Comentario comentarioInserido = new Comentario();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate data = LocalDate.parse(comentarioInserirDto.getDataCriacao(), formatter);
			
			comentarioInserido.setTexto(comentarioInserirDto.getTexto());
			comentarioInserido.setDataCriacao(data);
			comentarioInserido.setPost(comentarioInserirDto.getPost());
			
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

	public ComentarioDto atualizar(Long id, Comentario comentario) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		if (comentarioOpt.isPresent()) {
			comentario.setId(id);
			comentario = comentarioRepository.save(comentario);

			ComentarioDto comentarioDto = new ComentarioDto(comentario);

			return comentarioDto;
		}
		return null;
	}
}
