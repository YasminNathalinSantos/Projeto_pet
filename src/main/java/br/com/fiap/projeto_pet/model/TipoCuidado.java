package br.com.fiap.projeto_pet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Entidade que representa um tipo de cuidado veterinário (vacina, banho, consulta, etc.)")
@Entity
@Table(name = "tipo_cuidado")
public class TipoCuidado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O nome do tipo de cuidado é obrigatório")
	@Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres")
	@Column(name = "nome")
	@Schema(description = "Nome do tipo de cuidado", example = "Vacina V10")
	private String nome;

	@Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
	@Column(name = "descricao")
	@Schema(description = "Descrição detalhada do cuidado", example = "Vacina polivalente anual")
	private String descricao;

	@NotNull(message = "O intervalo em dias é obrigatório")
	@Min(value = 1, message = "O intervalo deve ser de no mínimo 1 dia")
	@Column(name = "intervalo_dias")
	@Schema(description = "Intervalo em dias para repetição do cuidado", example = "365")
	private Integer intervaloDias;

	@NotNull(message = "A prioridade é obrigatória")
	@Enumerated(EnumType.STRING)
	@Column(name = "prioridade")
	@Schema(description = "Prioridade do cuidado: ALTA, MEDIA ou BAIXA", example = "ALTA")
	private PrioridadeEnum prioridade;

	public TipoCuidado() {
	}

	public TipoCuidado(Long id, String nome, String descricao, Integer intervaloDias, PrioridadeEnum prioridade) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.intervaloDias = intervaloDias;
		this.prioridade = prioridade;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }

	public String getDescricao() { return descricao; }
	public void setDescricao(String descricao) { this.descricao = descricao; }

	public Integer getIntervaloDias() { return intervaloDias; }
	public void setIntervaloDias(Integer intervaloDias) { this.intervaloDias = intervaloDias; }

	public PrioridadeEnum getPrioridade() { return prioridade; }
	public void setPrioridade(PrioridadeEnum prioridade) { this.prioridade = prioridade; }

}
