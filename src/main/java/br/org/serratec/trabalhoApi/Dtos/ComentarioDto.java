package br.org.serratec.trabalhoApi.Dtos;

import java.time.format.DateTimeFormatter;

import br.org.serratec.trabalhoApi.model.Comentario;

public class ComentarioDto {

	private Long id;

	private String texto;

	private String dataCriacao;

	private Long post_id;
	
	public ComentarioDto() {

	}
	

	public ComentarioDto(Comentario comentario) {
		this.id = comentario.getId();
		this.texto = comentario.getTexto();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		String dataFormatada = comentario.getDataCriacao().format(formatter);  
		this.dataCriacao = dataFormatada;
		this.post_id = comentario.getPost().getId();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getPost_id() {
		return post_id;
	}

	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	
	
}
