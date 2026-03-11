package br.dev.rafael.okr.repositorios;

import br.dev.rafael.okr.entities.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
}
