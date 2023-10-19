package br.org.serratec.trabalhoApi.Dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UsuarioInserirDto {

	@NotBlank(message = "O nome não pode ser vazio.")
	private String nome;

	@NotBlank(message = "O sobrenome não pode ser vazio.")
	private String sobrenome;

	@NotBlank(message = "O email não pode ser vazio.")
	@Email(message = "Formato de email inválido.")
	private String email;

	@NotBlank(message = "A senha não pode ser vazia.")
	private String senha;

	@NotBlank(message = "A senha de confirmação não pode ser vazia.")
	private String confirmaSenha;

	@NotNull(message = "A data de nascimento não pode ser vazia.")
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Formato de data inválido. Use dd/MM/yyyy")
	private String dataNascimento;

	public UsuarioInserirDto() {

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
