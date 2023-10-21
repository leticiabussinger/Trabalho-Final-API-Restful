package br.org.serratec.trabalhoApi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.trabalhoApi.Dtos.PostDto;
import br.org.serratec.trabalhoApi.Dtos.PostInserirDto;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService postService;
	
	@GetMapping
	public ResponseEntity<List<PostDto>> listar() {
		if(postService.findAll().isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem posts cadastrados no sistema");
		}
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> buscar(@PathVariable Long id) {
		PostDto post = postService.findById(id);
		if (post == null) {
			throw new RecursoNaoEncontradoException("Não existe post cadastrado com o id " + id);
		}
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<PostDto>> buscarPostsUser(@PathVariable Long id){
		List<PostDto> posts = postService.buscarPostUser(id);
		if (posts == null || posts.isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem posts cadastrados para o usuario com o id " + id);
		}
		return ResponseEntity.ok(posts);
	}
	
	@PostMapping
	public ResponseEntity<PostDto> inserir(@Valid @RequestBody PostInserirDto post) {
		PostDto postInserido = postService.inserir(post);
		if(postInserido != null) {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(postInserido.getId())
					.toUri();
			
			return ResponseEntity.created(uri).body(postInserido);	
		}
		throw new RecursoNaoEncontradoException("Não é possivel adicionar um post, pois o usuario com o id " + post.getUsuario().getId() + " não existe.");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> atualizar(@PathVariable Long id, @Valid @RequestBody PostInserirDto postInserirDto) {
	
		PostDto postAtualizado = postService.atualizar(id, postInserirDto);
		
		if(postAtualizado != null) {
			return ResponseEntity.ok(postAtualizado);			
		}
		throw new RecursoNaoEncontradoException("Não existe post com o id " + id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		Boolean validate = postService.deletar(id);
		if(validate) {
			return ResponseEntity.noContent().build();			
		}
		throw new RecursoNaoEncontradoException("Não existe post com o id " + id);
	}

}
