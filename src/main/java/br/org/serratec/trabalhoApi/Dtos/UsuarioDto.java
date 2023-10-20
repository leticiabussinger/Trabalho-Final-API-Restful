package br.org.serratec.trabalhoApi.Dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.org.serratec.trabalhoApi.model.Post;
import br.org.serratec.trabalhoApi.model.Usuario;

public class UsuarioDto {

	private Long id;

	private String nome;

	private String sobrenome;

	private String email;

	private LocalDate dataNascimento;
	
//	private List<PostDto> posts;

	public UsuarioDto() {

	}

	public UsuarioDto(Usuario usuario) {
	    if (usuario != null) {
	        this.id = usuario.getId();
	        this.nome = usuario.getNome();
	        this.sobrenome = usuario.getSobrenome();
	        this.email = usuario.getEmail();
	        this.dataNascimento = usuario.getDataNascimento();
//	        this.posts = new ArrayList<>();
//	        if (usuario.getPosts() != null) {
//	            for (Post post : usuario.getPosts()) {
//	                this.posts.add(new PostDto(post));
//	            }
//	        }
	    }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

//	public List<PostDto> getPosts() {
//		return posts;
//	}
//
//	public void setPosts(List<PostDto> posts) {
//		this.posts = posts;
//	}


}
