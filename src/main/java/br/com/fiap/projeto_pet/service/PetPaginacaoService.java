package br.com.fiap.projeto_pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.projeto_pet.dto.PetDTO;
import br.com.fiap.projeto_pet.model.Pet;

@Service
public class PetPaginacaoService {

	@Autowired
	private PetCachingService cacheP;

	@Transactional(readOnly = true)
	public Page<PetDTO> paginar(PageRequest req) {
		Page<Pet> paginados = cacheP.findAll(req);
		Page<PetDTO> paginadosDTO = paginados.map(pet -> new PetDTO(pet));
		return paginadosDTO;
	}

}
