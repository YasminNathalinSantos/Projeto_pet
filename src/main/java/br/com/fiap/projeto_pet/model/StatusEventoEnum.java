package br.com.fiap.projeto_pet.model;

public enum StatusEventoEnum {

	PENDENTE("Pendente"),
	REALIZADO("Realizado"),
	ATRASADO("Atrasado"),
	CANCELADO("Cancelado");

	private String descricao;

	StatusEventoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
