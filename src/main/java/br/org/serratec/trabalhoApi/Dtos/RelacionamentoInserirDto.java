package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.org.serratec.trabalhoApi.model.Usuario;

public class RelacionamentoInserirDto {

	@NotNull(message = "O usuario seguido não pode ser vazio.")
	@Valid
	private Usuario usuarioSeguido;
	
	@NotNull(message = "O usuario seguidor não pode ser vazio.")
	@Valid
	private Usuario usuarioSeguidor;
	
	public RelacionamentoInserirDto() {
		
	}

	public Usuario getUsuarioSeguido() {
		return usuarioSeguido;
	}

	public void setUsuarioSeguido(Usuario usuarioSeguido) {
		this.usuarioSeguido = usuarioSeguido;
	}

	public Usuario getUsuarioSeguidor() {
		return usuarioSeguidor;
	}

	public void setUsuarioSeguidor(Usuario usuarioSeguidor) {
		this.usuarioSeguidor = usuarioSeguidor;
	}
	
}
