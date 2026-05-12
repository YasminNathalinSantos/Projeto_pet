package br.com.fiap.projeto_pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.projeto_pet.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
}
