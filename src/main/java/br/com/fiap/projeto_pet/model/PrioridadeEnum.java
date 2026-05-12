package br.com.fiap.projeto_pet.model;

public enum PrioridadeEnum {

	ALTA("Alta"),
	MEDIA("Média"),
	BAIXA("Baixa");

	private String descricao;

	PrioridadeEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
