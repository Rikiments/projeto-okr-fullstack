package br.dev.rafael.okr.repositorios;

import br.dev.rafael.okr.entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
}
