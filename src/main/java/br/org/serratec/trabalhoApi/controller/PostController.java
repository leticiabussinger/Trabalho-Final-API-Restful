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
import br.org.serratec.trabalhoApi.model.Post;
import br.org.serratec.trabalhoApi.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService postService;
	
	@GetMapping
	public ResponseEntity<List<PostDto>> listar() {
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> buscar(@PathVariable Long id) {
		PostDto post = postService.findById(id);
		if (post == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<List<PostDto>> buscarPostsUser(@PathVariable Long id){
		List<PostDto> posts = postService.buscarPostUser(id);
		if (posts == null || posts.isEmpty()) {
			return ResponseEntity.notFound().build();
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
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> atualizar(@PathVariable Long id, @Valid @RequestBody Post post) {
	
		PostDto postAtualizado = postService.atualizar(id, post);
		
		if(postAtualizado != null) {
			return ResponseEntity.ok(postAtualizado);			
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		Boolean validate = postService.deletar(id);
		if(validate) {
			return ResponseEntity.noContent().build();			
		}
		return ResponseEntity.notFound().build();
	}

}
