package br.com.fiap.projeto_pet.projection;

import java.time.LocalDate;

public interface EventoProjection {

	public String getPet_nome();
	public String getTipo_cuidado_nome();
	public LocalDate getEvento_data_prevista();
	public String getEvento_status();
	public String getTipo_prioridade();

}
