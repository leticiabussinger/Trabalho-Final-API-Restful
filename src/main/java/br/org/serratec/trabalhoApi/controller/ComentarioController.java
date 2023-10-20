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

import br.org.serratec.trabalhoApi.Dtos.ComentarioDto;
import br.org.serratec.trabalhoApi.Dtos.ComentarioInserirDto;
import br.org.serratec.trabalhoApi.model.Comentario;
import br.org.serratec.trabalhoApi.service.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
	ComentarioService comentarioService;

	@GetMapping
	public ResponseEntity<List<ComentarioDto>> listar() {
		return ResponseEntity.ok(comentarioService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComentarioDto> buscar(@PathVariable Long id) {
		ComentarioDto comentario = comentarioService.findById(id);
		if (comentario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(comentario);
	}

	@GetMapping("/post/{id}")
	public ResponseEntity<List<ComentarioDto>> buscarComentariosPost(@PathVariable Long id) {
		List<ComentarioDto> comentarios = comentarioService.buscarComentarioPost(id);
		if (comentarios == null || comentarios.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(comentarios);
	}

	@PostMapping
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
		return ResponseEntity.notFound().build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<ComentarioDto> atualizar(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {

		ComentarioDto comentarioAtualizado = comentarioService.atualizar(id, comentario);

		if (comentarioAtualizado != null) {
			return ResponseEntity.ok(comentarioAtualizado);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {

		Boolean validate = comentarioService.deletar(id);
		if (validate) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
