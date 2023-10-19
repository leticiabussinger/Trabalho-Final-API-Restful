package br.org.serratec.trabalhoApi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_post")
	private Long id;

	@NotBlank(message = "O conteudo do post não pode ser vazio.")
	@Column(nullable = false)
	private String conteudo;

	@NotNull(message = "A data de nascimento não pode ser vazia.")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de data inválido. Use dd/MM/yyyy")
	@Column(nullable = false)
	private LocalDate dataCriacao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	public Post() {
		
	}
	
	public Post(Long id, String conteudo, LocalDate dataCriacao) {
		this.id = id;
		this.conteudo = conteudo;
		this.dataCriacao = dataCriacao;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
