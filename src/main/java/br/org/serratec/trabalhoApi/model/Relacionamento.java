package br.org.serratec.trabalhoApi.model;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Relacionamento {

	@EmbeddedId()
	private RelacionamentoPK id = new RelacionamentoPK();
	
	private LocalDateTime dataInicioSeguimento;

	public Relacionamento() {

	}
	
	public Relacionamento(Usuario seguido, Usuario seguidor, LocalDateTime dataInicioSeguimento) {
		this.id.setUsuarioSeguido(seguido);
		this.id.setUsuarioSeguidor(seguidor);
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public RelacionamentoPK getId() {
		return id;
	}

	public void setId(RelacionamentoPK id) {
		this.id = id;
	}

	public LocalDateTime getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(LocalDateTime dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}
	
	
	
}
