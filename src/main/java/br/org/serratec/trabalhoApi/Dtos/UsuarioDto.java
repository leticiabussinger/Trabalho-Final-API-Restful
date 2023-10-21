package br.org.serratec.trabalhoApi.Dtos;

import java.time.format.DateTimeFormatter;

import br.org.serratec.trabalhoApi.model.Usuario;

public class UsuarioDto {

	private Long id;

	private String nome;

	private String sobrenome;

	private String email;

	private String dataNascimento;

	public UsuarioDto() {

	}

	public UsuarioDto(Usuario usuario) {
		if (usuario != null) {
			this.id = usuario.getId();
			this.nome = usuario.getNome();
			this.sobrenome = usuario.getSobrenome();
			this.email = usuario.getEmail();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
			String dataFormatada = usuario.getDataNascimento().format(formatter);
			this.dataNascimento = dataFormatada;
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

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
