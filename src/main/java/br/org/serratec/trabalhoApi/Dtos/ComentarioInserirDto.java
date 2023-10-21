package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.org.serratec.trabalhoApi.model.Post;



public class ComentarioInserirDto {

	@NotBlank(message = "O texto do comentario não pode ser vazio.")
	private String texto;
	
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


	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

}
