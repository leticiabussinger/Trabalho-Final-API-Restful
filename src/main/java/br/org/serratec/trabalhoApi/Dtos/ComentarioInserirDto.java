package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.org.serratec.trabalhoApi.model.Post;



public class ComentarioInserirDto {

	@NotBlank(message = "O texto do comentario não pode ser vazio.")
	private String texto;
	
	@NotNull(message = "A data de criação não pode ser vazia.")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de data inválido. Use dd/MM/yyyy")
	private String dataCriacao;
	
	@NotNull(message = "O post não pode ser vazio.")
	private Post post;
	
	public ComentarioInserirDto() {
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

}
