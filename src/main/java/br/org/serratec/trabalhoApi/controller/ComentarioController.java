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

import br.org.serratec.trabalhoApi.Dtos.ComentarioAtualizarDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioInserirDto;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.service.ComentarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
	ComentarioService comentarioService;

	@GetMapping
	@ApiOperation(value ="Listar todos os comentarios", notes = "Listagem de todos os comentarios cadastrados no sistema")
	public ResponseEntity<List<ComentarioDto>> listar() {
		
		List<ComentarioDto> comentarios = comentarioService.findAll();
		
		if(comentarios == null) {
			throw new RecursoNaoEncontradoException("Não existem comentarios cadastrados no sistema");
		}
		return ResponseEntity.ok(comentarios);
	}

	@GetMapping("/{id}")
	@ApiOperation(value ="Listar um comentario por id", notes = "Listagem do comentario com um id especifico")
	public ResponseEntity<ComentarioDto> buscar(@PathVariable Long id) {
		ComentarioDto comentario = comentarioService.findById(id);
		if (comentario == null) {
			throw new RecursoNaoEncontradoException("Não existe comentario com o id " + id );
		}
		return ResponseEntity.ok(comentario);
	}

	@GetMapping("/post/{id}")
	@ApiOperation(value ="Listar os comentarios por id do post", notes = "Listagem de comentarios com um id de um post especifico")
	public ResponseEntity<List<ComentarioDto>> buscarComentariosPost(@PathVariable Long id) {
		List<ComentarioDto> comentarios = comentarioService.buscarComentarioPost(id);
		if (comentarios == null || comentarios.isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem comentarios cadastrados para o post com o id " + id);
		}
		return ResponseEntity.ok(comentarios);
	}

	@PostMapping
	@ApiOperation(value ="Adicionar um comentario", notes = "Adiciona um comentario a um post especifico no sistema se o usuario que comentou está seguindo o criador do post")
	public ResponseEntity<ComentarioDto> inserir(@Valid @RequestBody ComentarioInserirDto comentario) {
		ComentarioDto comentarioInserido = comentarioService.inserir(comentario);
		
		if(comentarioInserido != null) {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(comentarioInserido.getId())
					.toUri();
			
			return ResponseEntity.created(uri).body(comentarioInserido);		
		}
		throw new RecursoNaoEncontradoException("Não é possivel inserir um comentario, pois o post de id " + comentario.getPost().getId() + " não existe!");

	}

	@PutMapping("/{id}")
	@ApiOperation(value ="Atualizar um comentario por id", notes = "Atualiza o conteudo de um comentario exitente no sistema")
	public ResponseEntity<ComentarioDto> atualizar(@PathVariable Long id, @Valid @RequestBody ComentarioAtualizarDto comentarioAtualizarDto) {

		ComentarioDto comentarioAtualizado = comentarioService.atualizar(id, comentarioAtualizarDto);

		if (comentarioAtualizado != null) {
			return ResponseEntity.ok(comentarioAtualizado);
		}
		throw new RecursoNaoEncontradoException("Não existe comentario com o id " + id );
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value ="Deletar um comentario por id", notes = "Deleta um comentario existente em um post no sistema")
	public ResponseEntity<Void> remover(@PathVariable Long id) {

		Boolean validate = comentarioService.deletar(id);
		if (validate) {
			return ResponseEntity.noContent().build();
		}
		throw new RecursoNaoEncontradoException("Não existe comentario com o id " + id );
	}

}
