package br.com.fiap.projeto_pet.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa um evento de cuidado agendado ou realizado para um pet")
@Entity
@Table(name = "evento_cuidado")
public class EventoCuidado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fk_pet")
	@NotNull(message = "O pet é obrigatório")
	@Schema(description = "Pet relacionado ao evento de cuidado")
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "fk_tipo_cuidado")
	@NotNull(message = "O tipo de cuidado é obrigatório")
	@Schema(description = "Tipo de cuidado do evento")
	private TipoCuidado tipoCuidado;

	@NotNull(message = "A data prevista é obrigatória")
	@Column(name = "data_prevista")
	@Schema(description = "Data prevista para realização do cuidado", example = "2025-06-01")
	private LocalDate dataPrevista;

	@Column(name = "data_realizada")
	@Schema(description = "Data em que o cuidado foi efetivamente realizado", example = "2025-06-01")
	private LocalDate dataRealizada;

	@NotNull(message = "O status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@Schema(description = "Status do evento: PENDENTE, REALIZADO, ATRASADO ou CANCELADO", example = "PENDENTE")
	private StatusEventoEnum status;

	@Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
	@Column(name = "observacao")
	@Schema(description = "Observação sobre o evento de cuidado", example = "Vacina aplicada sem intercorrências")
	private String observacao;

	public void transferirEvento(EventoCuidado evento) {
		this.pet = evento.getPet();
		this.tipoCuidado = evento.getTipoCuidado();
		this.dataPrevista = evento.getDataPrevista();
		this.dataRealizada = evento.getDataRealizada();
		this.status = evento.getStatus();
		this.observacao = evento.getObservacao();
	}

	public EventoCuidado() {
	}

	public EventoCuidado(Long id, Pet pet, TipoCuidado tipoCuidado, LocalDate dataPrevista,
			LocalDate dataRealizada, StatusEventoEnum status, String observacao) {
		super();
		this.id = id;
		this.pet = pet;
		this.tipoCuidado = tipoCuidado;
		this.dataPrevista = dataPrevista;
		this.dataRealizada = dataRealizada;
		this.status = status;
		this.observacao = observacao;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Pet getPet() { return pet; }
	public void setPet(Pet pet) { this.pet = pet; }

	public TipoCuidado getTipoCuidado() { return tipoCuidado; }
	public void setTipoCuidado(TipoCuidado tipoCuidado) { this.tipoCuidado = tipoCuidado; }

	public LocalDate getDataPrevista() { return dataPrevista; }
	public void setDataPrevista(LocalDate dataPrevista) { this.dataPrevista = dataPrevista; }

	public LocalDate getDataRealizada() { return dataRealizada; }
	public void setDataRealizada(LocalDate dataRealizada) { this.dataRealizada = dataRealizada; }

	public StatusEventoEnum getStatus() { return status; }
	public void setStatus(StatusEventoEnum status) { this.status = status; }

	public String getObservacao() { return observacao; }
	public void setObservacao(String observacao) { this.observacao = observacao; }

}
