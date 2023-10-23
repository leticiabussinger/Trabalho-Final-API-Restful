package br.org.serratec.trabalhoApi.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.trabalhoApi.Dtos.RelacionamentoDto;
import br.org.serratec.trabalhoApi.Dtos.RelacionamentoInserirDto;
import br.org.serratec.trabalhoApi.exception.RecursoNaoEncontradoException;
import br.org.serratec.trabalhoApi.service.RelacionamentoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relacionamentos")
public class RelacionamentoController {

	@Autowired
	RelacionamentoService relacionamentoService;

	@GetMapping("/seguido/{idSeguido}/seguidor/{idSeguidor}")
	@ApiOperation(value ="Listar um relacionamento especifico pelo idSeguido e idSeguidor", notes = "Listagem de um relacionamento especifico entre dois usuarios, com id de quem é seguido e com id de seu seguidor")
	public ResponseEntity<RelacionamentoDto> buscar(@PathVariable Long idSeguido, @PathVariable Long idSeguidor) {

		RelacionamentoDto relacionamento = relacionamentoService.findById(idSeguido, idSeguidor);
		if (relacionamento == null) {
			throw new RecursoNaoEncontradoException(
					"O usuario com id " + idSeguido + " não é seguido pelo usuario com id " + idSeguidor);
		}
		return ResponseEntity.ok(relacionamento);
	}

	@GetMapping("/usuario/{id}/seguidores")
	@ApiOperation(value ="Listar os seguidores de um usuario por id", notes = "Listagem dos seguidores de um usuario com um id especifico")
	public ResponseEntity<List<RelacionamentoDto>> buscarSeguidoresUser(@PathVariable Long id) {
		List<RelacionamentoDto> relacionamentos = relacionamentoService.buscarSeguidoresUser(id);
		if (relacionamentos == null || relacionamentos.isEmpty()) {
			throw new RecursoNaoEncontradoException("O usuario com id " + id + " não possui seguidores");
		}
		return ResponseEntity.ok(relacionamentos);
	}

	@GetMapping("/usuario/{id}/seguindo")
	@ApiOperation(value ="Listar quem um usuario segue por id", notes = "Listagem de quem um usuario com um id especifico segue")
	public ResponseEntity<List<RelacionamentoDto>> buscarSeguindoUser(@PathVariable Long id) {
		List<RelacionamentoDto> relacionamentos = relacionamentoService.buscarSeguindoUser(id);
		if (relacionamentos == null || relacionamentos.isEmpty()) {
			throw new RecursoNaoEncontradoException("O usuario com id " + id + " não segue ninguem");
		}
		return ResponseEntity.ok(relacionamentos);
	}

	@PostMapping
	@ApiOperation(value ="Adicionar a um relacionamento entre dois usuarios", notes = "Adiciona um relacionamento entre dois usuarios cadastrados no sistema a partir seus ids")
	public ResponseEntity<RelacionamentoDto> inserir(
			@Valid @RequestBody RelacionamentoInserirDto relacionamentoInserir) {
		RelacionamentoDto relacionamentoInserido = relacionamentoService.inserir(relacionamentoInserir);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/seguido/{idSeguido}/seguidor/{idSeguidor}")
				.buildAndExpand(relacionamentoInserido.getUsuario_seguido_id(),
						relacionamentoInserido.getUsuario_seguidor_id())
				.toUri();

		return ResponseEntity.created(uri).body(relacionamentoInserido);
	}

	@Transactional
	@DeleteMapping("/seguido/{idSeguido}/seguidor/{idSeguidor}")
	@ApiOperation(value ="Deletar um relacionamento especifico pelo idSeguido e idSeguidor", notes = "Deleta um relacionamento especifico entre dois usuarios, com id de quem é seguido e com id de seu seguidor")
	public ResponseEntity<Void> remover(@PathVariable Long idSeguido, @PathVariable Long idSeguidor) {

		Boolean validate = relacionamentoService.deletar(idSeguido, idSeguidor);
		if (validate) {
			return ResponseEntity.noContent().build();
		}
		throw new RecursoNaoEncontradoException(
				"Não é possivel deletar, pois o usuario com id " + idSeguido + " não é seguido pelo usuario com id " + idSeguidor);
	}
}
