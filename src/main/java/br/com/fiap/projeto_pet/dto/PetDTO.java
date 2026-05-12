package br.com.fiap.projeto_pet.dto;

import br.com.fiap.projeto_pet.model.EspecieEnum;
import br.com.fiap.projeto_pet.model.Pet;
import br.com.fiap.projeto_pet.model.Tutor;

import java.time.LocalDate;

public class PetDTO {

	private Tutor tutor;
	private String nome;
	private EspecieEnum especie;
	private String raca;
	private LocalDate dataNascimento;
	private Double peso;
	private Boolean ativo;

	public PetDTO() {
	}

	public PetDTO(Pet pet) {
		this.tutor = pet.getTutor();
		this.nome = pet.getNome();
		this.especie = pet.getEspecie();
		this.raca = pet.getRaca();
		this.dataNascimento = pet.getDataNascimento();
		this.peso = pet.getPeso();
		this.ativo = pet.getAtivo();
	}

	public Tutor getTutor() { return tutor; }
	public void setTutor(Tutor tutor) { this.tutor = tutor; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public EspecieEnum getEspecie() { return especie; }
	public void setEspecie(EspecieEnum especie) { this.especie = especie; }

	public String getRaca() { return raca; }
	public void setRaca(String raca) { this.raca = raca; }

	public LocalDate getDataNascimento() { return dataNascimento; }
	public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

	public Double getPeso() { return peso; }
	public void setPeso(Double peso) { this.peso = peso; }

	public Boolean getAtivo() { return ativo; }
	public void setAtivo(Boolean ativo) { this.ativo = ativo; }

}
