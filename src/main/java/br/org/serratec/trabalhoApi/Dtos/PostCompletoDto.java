package br.org.serratec.trabalhoApi.Dtos;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.org.serratec.trabalhoApi.model.Comentario;
import br.org.serratec.trabalhoApi.model.Post;

public class PostCompletoDto {

	private Long id;

	private String conteudo;

	private String dataCriacao;
	
	private List<ComentarioCompletoDto> comentarios;
	
	
	public PostCompletoDto() {
	}


	public PostCompletoDto(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		String dataFormatada = post.getDataCriacao().format(formatter);  
		this.dataCriacao = dataFormatada;
		this.comentarios = new ArrayList<>();
		for (Comentario comentario : post.getComentarios()) {
			this.comentarios.add(new ComentarioCompletoDto(comentario));
		}
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public List<ComentarioCompletoDto> getComentarios() {
		return comentarios;
	}


	public void setComentarios(List<ComentarioCompletoDto> comentarios) {
		this.comentarios = comentarios;
	}
	
	
}
