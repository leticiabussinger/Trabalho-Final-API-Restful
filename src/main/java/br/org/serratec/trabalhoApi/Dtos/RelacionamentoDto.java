package br.org.serratec.trabalhoApi.Dtos;

import java.time.format.DateTimeFormatter;

import br.org.serratec.trabalhoApi.model.Relacionamento;

public class RelacionamentoDto {

	private Long usuario_seguido_id;

	private String usuario_seguido_nome;

	private Long usuario_seguidor_id;

	private String usuario_seguidor_nome;

	private String dataInicioSeguimento;
	
	public RelacionamentoDto() {
		
	}

	public RelacionamentoDto(Relacionamento relacionamento) {
		this.usuario_seguido_id = relacionamento.getId().getUsuarioSeguido().getId();
		this.usuario_seguido_nome = relacionamento.getId().getUsuarioSeguido().getNome();
		this.usuario_seguidor_id = relacionamento.getId().getUsuarioSeguidor().getId();
		this.usuario_seguidor_nome = relacionamento.getId().getUsuarioSeguidor().getNome();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
		String dataFormatada = relacionamento.getDataInicioSeguimento().format(formatter);
		this.dataInicioSeguimento = dataFormatada;
	}

	

	public Long getUsuario_seguido_id() {
		return usuario_seguido_id;
	}

	public void setUsuario_seguido_id(Long usuario_seguido_id) {
		this.usuario_seguido_id = usuario_seguido_id;
	}

	public String getUsuario_seguido_nome() {
		return usuario_seguido_nome;
	}

	public void setUsuario_seguido_nome(String usuario_seguido_nome) {
		this.usuario_seguido_nome = usuario_seguido_nome;
	}

	public Long getUsuario_seguidor_id() {
		return usuario_seguidor_id;
	}

	public void setUsuario_seguidor_id(Long usuario_seguidor_id) {
		this.usuario_seguidor_id = usuario_seguidor_id;
	}

	public String getUsuario_seguidor_nome() {
		return usuario_seguidor_nome;
	}

	public void setUsuario_seguidor_nome(String usuario_seguidor_nome) {
		this.usuario_seguidor_nome = usuario_seguidor_nome;
	}

	public String getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(String dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

}
