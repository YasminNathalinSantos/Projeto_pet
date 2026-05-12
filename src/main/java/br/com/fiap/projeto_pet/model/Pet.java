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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa o pet cadastrado no sistema")
@Entity
@Table(name = "pet")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "fk_tutor")
	@Schema(description = "Tutor responsável pelo pet")
	private Tutor tutor;

	@NotEmpty(message = "O nome do pet é obrigatório")
	@Size(min = 1, max = 60, message = "O nome deve ter entre 1 e 60 caracteres")
	@Column(name = "nome")
	@Schema(description = "Nome do pet", example = "Rex")
	private String nome;

	@NotNull(message = "A espécie é obrigatória")
	@Enumerated(EnumType.STRING)
	@Column(name = "especie")
	@Schema(description = "Espécie do pet: CACHORRO, GATO, AVE, ROEDOR ou REPTIL", example = "CACHORRO")
	private EspecieEnum especie;

	@Size(max = 60, message = "A raça deve ter no máximo 60 caracteres")
	@Column(name = "raca")
	@Schema(description = "Raça do pet", example = "Labrador")
	private String raca;

	@PastOrPresent(message = "A data de nascimento deve ser atual ou passada")
	@Column(name = "data_nascimento")
	@Schema(description = "Data de nascimento do pet", example = "2020-05-10")
	private LocalDate dataNascimento;

	@DecimalMin(value = "0.1", message = "O peso deve ser de no mínimo 0.1 kg")
	@DecimalMax(value = "200.0", message = "O peso deve ser de no máximo 200.0 kg")
	@Column(name = "peso")
	@Schema(description = "Peso do pet em kg", example = "28.5")
	private Double peso;

	@Column(name = "ativo")
	@Schema(description = "Indica se o pet está ativo no sistema", example = "true")
	private Boolean ativo;

	public void transferirPet(Pet pet) {
		this.tutor = pet.getTutor();
		this.nome = pet.getNome();
		this.especie = pet.getEspecie();
		this.raca = pet.getRaca();
		this.dataNascimento = pet.getDataNascimento();
		this.peso = pet.getPeso();
		this.ativo = pet.getAtivo();
	}

	public Pet() {
	}

	public Pet(Long id, Tutor tutor, String nome, EspecieEnum especie, String raca,
			LocalDate dataNascimento, Double peso, Boolean ativo) {
		super();
		this.id = id;
		this.tutor = tutor;
		this.nome = nome;
		this.especie = especie;
		this.raca = raca;
		this.dataNascimento = dataNascimento;
		this.peso = peso;
		this.ativo = ativo;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

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
