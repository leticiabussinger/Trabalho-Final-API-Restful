package br.org.serratec.trabalhoApi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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

import br.org.serratec.trabalhoApi.Dtos.PostAtualizarDto;
import br.org.serratec.trabalhoApi.Dtos.PostDto;
import br.org.serratec.trabalhoApi.Dtos.PostInserirDto;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.service.PostService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService postService;
	
	@GetMapping
	@ApiOperation(value ="Listar todos os posts", notes = "Listagem de todos os posts cadastrados no sistema")
	public ResponseEntity<List<PostDto>> listar() {
		if(postService.findAll().isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem posts cadastrados no sistema");
		}
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/paginado")
	@ApiOperation(value ="Listar todos os posts", notes = "Listagem de todos os posts cadastrados no sistema de forma paginada")
	public ResponseEntity<Page<PostDto>> listarPaginado(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		if(postService.findAll().isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem posts cadastrados no sistema");
		}
		return ResponseEntity.ok(postService.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value ="Listar um post por id", notes = "Listagem do post com um id especifico")
	public ResponseEntity<PostDto> buscar(@PathVariable Long id) {
		PostDto post = postService.findById(id);
		if (post == null) {
			throw new RecursoNaoEncontradoException("Não existe post cadastrado com o id " + id);
		}
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/usuario/{id}")
	@ApiOperation(value ="Listar os posts por id do usuario", notes = "Listagem de posts com um id de um usuario especifico")
	public ResponseEntity<List<PostDto>> buscarPostsUser(@PathVariable Long id){
		List<PostDto> posts = postService.buscarPostUser(id);
		if (posts == null || posts.isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem posts cadastrados para o usuario com o id " + id);
		}
		return ResponseEntity.ok(posts);
	}
	
	@PostMapping
	@ApiOperation(value ="Adicionar um post", notes = "Adiciona um post a um usuario especifico no sistema")
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
	@ApiOperation(value ="Atualizar um post por id", notes = "Atualiza o conteudo de um post exitente no sistema")
	public ResponseEntity<PostDto> atualizar(@PathVariable Long id, @Valid @RequestBody PostAtualizarDto postAtualizarDto) {
	
		PostDto postAtualizado = postService.atualizar(id, postAtualizarDto);
		
		if(postAtualizado != null) {
			return ResponseEntity.ok(postAtualizado);			
		}
		throw new RecursoNaoEncontradoException("Não existe post com o id " + id);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value ="Deletar um post por id", notes = "Deleta um post existente de um usuario no sistema")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		Boolean validate = postService.deletar(id);
		if(validate) {
			return ResponseEntity.noContent().build();			
		}
		throw new RecursoNaoEncontradoException("Não existe post com o id " + id);
	}

}
