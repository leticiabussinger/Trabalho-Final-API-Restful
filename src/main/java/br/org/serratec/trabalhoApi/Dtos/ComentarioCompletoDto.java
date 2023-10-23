package br.org.serratec.trabalhoApi.Dtos;

import java.time.format.DateTimeFormatter;

import br.org.serratec.trabalhoApi.model.Comentario;

public class ComentarioCompletoDto {
	
	private Long id;

	private String texto;

	private String dataCriacao;
	
	private Long id_usuario_autor;
	
	public ComentarioCompletoDto() {

	}
	

	public ComentarioCompletoDto(Comentario comentario) {
		this.id = comentario.getId();
		this.texto = comentario.getTexto();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		String dataFormatada = comentario.getDataCriacao().format(formatter);  
		this.dataCriacao = dataFormatada;
		this.id_usuario_autor = comentario.getUsuario().getId();
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


	public Long getId_usuario_autor() {
		return id_usuario_autor;
	}


	public void setId_usuario_autor(Long id_usuario_autor) {
		this.id_usuario_autor = id_usuario_autor;
	}
	
	
}
