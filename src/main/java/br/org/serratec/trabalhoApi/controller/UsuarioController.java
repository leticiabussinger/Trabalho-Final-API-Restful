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

import br.org.serratec.trabalhoApi.Dtos.UsuarioDto;
import br.org.serratec.trabalhoApi.Dtos.UsuarioInserirDto;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.service.UsuarioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	@ApiOperation(value ="Listar todos os usuarios", notes = "Listagem de todos os usuarios cadastrados no sistema")
	public ResponseEntity<List<UsuarioDto>> listar() {
		if(usuarioService.findAll().isEmpty()) {
			throw new RecursoNaoEncontradoException("Não existem usuarios cadastrados no sistema");
		}
		return ResponseEntity.ok(usuarioService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDto> buscar(@PathVariable Long id) {
		UsuarioDto usuarioDto = usuarioService.findById(id);
		if (usuarioDto == null) {
			throw new RecursoNaoEncontradoException("Não existe usuario com o id " + id);
		}
		return ResponseEntity.ok(usuarioDto);
	}
	

	@PostMapping
    public ResponseEntity<UsuarioDto> inserir(@Valid @RequestBody UsuarioInserirDto usuarioInserirDTO) {
        UsuarioDto usuarioDto = usuarioService.inserir(usuarioInserirDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(usuarioDto);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInserirDto usuario) {
	
		UsuarioDto usuarioDto = usuarioService.atualizar(id, usuario);
		
		if(usuarioDto != null) {
			return ResponseEntity.ok(usuarioDto);			
		}
		throw new RecursoNaoEncontradoException("Não existe usuario com o id " + id );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		Boolean validate = usuarioService.deletar(id);
		if(validate) {
			return ResponseEntity.noContent().build();			
		}
		throw new RecursoNaoEncontradoException("Não existe usuario com o id " + id);
	}
}
