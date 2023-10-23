package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class PostAtualizarDto {

	@ApiModelProperty(example = "conteudo atualizado do post")
	@NotBlank(message = "O conteudo do post não pode ser vazio.")
	private String conteudo;

	public PostAtualizarDto() {
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
