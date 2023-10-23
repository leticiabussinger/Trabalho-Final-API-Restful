package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ComentarioAtualizarDto {

	@ApiModelProperty(example = "conteudo atualizado do comentario")
	@NotBlank(message = "O texto do comentario não pode ser vazio.")
	private String texto;

	
	public ComentarioAtualizarDto() {
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
