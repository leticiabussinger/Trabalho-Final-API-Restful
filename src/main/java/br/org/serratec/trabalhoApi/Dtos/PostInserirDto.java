package br.org.serratec.trabalhoApi.Dtos;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.org.serratec.trabalhoApi.model.Usuario;
import io.swagger.annotations.ApiModelProperty;

public class PostInserirDto {

	@ApiModelProperty(example = "conteudo do post")
	@NotBlank(message = "O conteudo do post não pode ser vazio.")
	private String conteudo;
	
	@ApiModelProperty(dataType = "List", example = "{id}", notes = "O valor do id não pode ser nulo.")
	@NotNull(message = "O usuario não pode ser vazio.")
	private Usuario usuario;

	
	
	public PostInserirDto() {
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
