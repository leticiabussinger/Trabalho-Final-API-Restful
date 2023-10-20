package br.org.serratec.trabalhoApi.Dtos;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.org.serratec.trabalhoApi.model.Usuario;

public class PostInserirDto {

	@NotBlank(message = "O conteudo do post não pode ser vazio.")
	private String conteudo;
	
	@NotNull(message = "A data de criação não pode ser vazia.")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de data inválido. Use dd/MM/yyyy")
	private String dataCriacao;
	
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

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
