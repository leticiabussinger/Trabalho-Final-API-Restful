package br.org.serratec.trabalhoApi.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.model.Foto;
import br.org.serratec.trabalhoApi.service.FotoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	FotoService fotoService;
	
	@GetMapping("/usuario/{id}")
	@ApiOperation(value ="Listar a foto por id do usuario", notes = "Listagem da foto com um id de um usuario especifico")
	public ResponseEntity<byte[]> buscarFotoUser(@PathVariable Long id){
		Foto foto = fotoService.buscarPorIdUsuario(id);
		
		if (foto == null) {
			throw new RecursoNaoEncontradoException("Não existe foto cadastrada para o usuario com o id " + id);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, foto.getTipo());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(foto.getDados().length));
		return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
	}
	
	@PostMapping("usuario/{id}")
	@ApiOperation(value ="Adicionar a foto por id do usuario", notes = "Adiciona uma foto para um usuario com um id especifico")
	public ResponseEntity<byte[]> inserir(@PathVariable Long id, @RequestPart MultipartFile file) throws IOException {		
		Foto fotoInserida = fotoService.inserir(id, file);
		if(fotoInserida != null) {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.build()
					.toUri();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, fotoInserida.getTipo());
			headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fotoInserida.getDados().length));
			headers.add(HttpHeaders.LOCATION, String.valueOf(uri));
			return new ResponseEntity<>(fotoInserida.getDados(), headers, HttpStatus.CREATED);
		}
		throw new RecursoNaoEncontradoException("Não é possivel adicionar a foto, pois o usuario com o id " + id + " não existe.");
	}
	
	@PutMapping("usuario/{id}")
	@ApiOperation(value ="Atualizar a foto por id do usuario", notes = "Atualiza a foto de um usuario com um id especifico")
	public ResponseEntity<byte[]> atualizar(@PathVariable Long id, @RequestPart MultipartFile file) throws IOException {		
		Foto fotoAtualizada = fotoService.atualizar(id, file);
		if(fotoAtualizada != null) {
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.build()
					.toUri();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, fotoAtualizada.getTipo());
			headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(fotoAtualizada.getDados().length));
			headers.add(HttpHeaders.LOCATION, String.valueOf(uri));
			return new ResponseEntity<>(fotoAtualizada.getDados(), headers, HttpStatus.CREATED);
		}
		throw new RecursoNaoEncontradoException("Não é possivel atualizar a foto, pois o usuario com o id " + id + " não existe.");
	}
	
	@DeleteMapping("usuario/{id}")
	@ApiOperation(value ="Deletar a foto por id do usuario", notes = "Deleta a foto de um usuario com um id especifico")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		
		Boolean validate = fotoService.deletar(id);
		if(validate) {
			return ResponseEntity.noContent().build();			
		}
		throw new RecursoNaoEncontradoException("Não existe uma foto cadastrada para o usuario com id " + id);
	}
	
	
}
