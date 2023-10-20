package br.org.serratec.trabalhoApi.Dtos;

import java.time.LocalDate;

import br.org.serratec.trabalhoApi.model.Post;

public class PostDto {

	private Long id;

	private String conteudo;

	private LocalDate dataCriacao;

	private Long usuario_id;

	public PostDto() {

	}

	public PostDto(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataCriacao = post.getDataCriacao();
		this.usuario_id = post.getUsuario().getId();
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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}

}
