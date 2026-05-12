package br.com.fiap.projeto_pet.dto;

import br.com.fiap.projeto_pet.model.EventoCuidado;
import br.com.fiap.projeto_pet.model.Pet;
import br.com.fiap.projeto_pet.model.StatusEventoEnum;
import br.com.fiap.projeto_pet.model.TipoCuidado;

import java.time.LocalDate;

public class EventoCuidadoDTO {

	private Pet pet;
	private TipoCuidado tipoCuidado;
	private LocalDate dataPrevista;
	private LocalDate dataRealizada;
	private StatusEventoEnum status;
	private String observacao;

	public EventoCuidadoDTO() {
	}

	public EventoCuidadoDTO(EventoCuidado evento) {
		this.pet = evento.getPet();
		this.tipoCuidado = evento.getTipoCuidado();
		this.dataPrevista = evento.getDataPrevista();
		this.dataRealizada = evento.getDataRealizada();
		this.status = evento.getStatus();
		this.observacao = evento.getObservacao();
	}

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
