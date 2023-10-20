package br.org.serratec.trabalhoApi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.trabalhoApi.Dtos.PostDto;
import br.org.serratec.trabalhoApi.Dtos.PostInserirDto;
import br.org.serratec.trabalhoApi.model.Post;
import br.org.serratec.trabalhoApi.model.Usuario;
import br.org.serratec.trabalhoApi.repository.PostRepository;
import br.org.serratec.trabalhoApi.repository.UsuarioRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<PostDto> findAll() {
		List<Post> posts = postRepository.findAll();
		
		List<PostDto> postDTO = new ArrayList<>();
		
		for (Post post : posts) {
			postDTO.add(new PostDto(post));
		}
		return postDTO;
	}

	public PostDto findById(Long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		if (postOpt.isEmpty()) {
			return null;
		}
		
		PostDto postDto = new PostDto(postOpt.get());
		
		return postDto;
	}
	
	public List<PostDto> buscarPostUser(Long id) {
		
		Optional<List<Post>> posts = postRepository.findByUsuarioId(id);
		if(posts.isPresent()) {
			List<PostDto> postDTO = new ArrayList<>();
			
			for (Post post : posts.get()) {
				postDTO.add(new PostDto(post));
			}
			return postDTO;			
		}
		return null;
	}

	public PostDto inserir(PostInserirDto postInserirDto) {
		
		Optional<Usuario> user = usuarioRepository.findById(postInserirDto.getUsuario().getId());
		
		if(user.isPresent()) {
			Post postInserido = new Post();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate data = LocalDate.parse(postInserirDto.getDataCriacao(), formatter);
			
			
			postInserido.setConteudo(postInserirDto.getConteudo());
			postInserido.setDataCriacao(data);
			postInserido.setUsuario(postInserirDto.getUsuario());
			
			postInserido = postRepository.save(postInserido);
			
			PostDto postDto = new PostDto(postInserido);
			
			return postDto;
		}
		return null;
	}

	public Boolean deletar(Long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		if (postOpt.isPresent()) {
			postRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public PostDto atualizar(Long id, Post post) {
		Optional<Post> postOpt = postRepository.findById(id);
		if (postOpt.isPresent()) {
			post.setId(id);
			post = postRepository.save(post);
			
			PostDto postDto = new PostDto(post);
			
			return postDto;
		}
		return null;
	}
}
