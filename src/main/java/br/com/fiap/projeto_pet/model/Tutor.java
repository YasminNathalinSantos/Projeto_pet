package br.com.fiap.projeto_pet.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa o tutor responsável pelo pet")
@Entity
@Table(name = "tutor")
public class Tutor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome do tutor é um campo obrigatório")
	@Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
	@Column(name = "nome")
	@Schema(description = "Nome completo do tutor", example = "Ana Lima")
	private String nome;

	@NotEmpty(message = "O CPF é um campo obrigatório")
	@Size(min = 14, max = 14, message = "O CPF deve estar no formato 000.000.000-00")
	@Column(name = "cpf", unique = true)
	@Schema(description = "CPF do tutor no formato 000.000.000-00", example = "123.456.789-00")
	private String cpf;

	@NotEmpty(message = "O e-mail é um campo obrigatório")
	@Email(message = "O e-mail informado é inválido")
	@Column(name = "email")
	@Schema(description = "E-mail de contato do tutor", example = "ana@email.com")
	private String email;

	@Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
	@Column(name = "telefone")
	@Schema(description = "Telefone de contato do tutor", example = "11999990001")
	private String telefone;

	@PastOrPresent(message = "A data de cadastro deve ser atual ou passada")
	@Column(name = "data_cadastro")
	@Schema(description = "Data de cadastro do tutor no sistema", example = "2024-01-10")
	private LocalDate dataCadastro;

	public Tutor() {
	}

	public Tutor(Long id, String nome, String cpf, String email, String telefone, LocalDate dataCadastro) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.dataCadastro = dataCadastro;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }

	public LocalDate getDataCadastro() { return dataCadastro; }
	public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

}
