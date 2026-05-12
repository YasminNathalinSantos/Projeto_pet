package br.com.fiap.projeto_pet.model;

public enum StatusUsuarioEnum {

	ATIVO("Ativo"),
	INATIVO("Inativo"),
	TRANCADO("Trancado"),
	BLOQUEADO("Bloqueado"),
	FORMADO("Formado"),
	CANCELADO("Cancelado"),
	EM_MOBILIDADE("Em Mobilidade");

	private String descricao;

	StatusUsuarioEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
