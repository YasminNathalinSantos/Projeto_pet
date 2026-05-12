package br.com.fiap.projeto_pet.model;

public enum EspecieEnum {

	CACHORRO("Cachorro"),
	GATO("Gato"),
	AVE("Ave"),
	ROEDOR("Roedor"),
	REPTIL("Réptil");

	private String descricao;

	EspecieEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
