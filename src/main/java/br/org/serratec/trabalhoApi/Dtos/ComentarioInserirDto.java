package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.org.serratec.trabalhoApi.model.Post;
import io.swagger.annotations.ApiModelProperty;


public class ComentarioInserirDto {

	@ApiModelProperty(example = "conteudo do post")
	@NotBlank(message = "O texto do comentario não pode ser vazio.")
	private String texto;
	
	@ApiModelProperty(dataType = "List", example = "{id}", notes = "O valor do id não pode ser nulo.")
	@NotNull(message = "O post não pode ser vazio.")
	@Valid
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
