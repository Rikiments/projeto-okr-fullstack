package br.dev.rafael.okr.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String descricao;
    private boolean concluida;
}
