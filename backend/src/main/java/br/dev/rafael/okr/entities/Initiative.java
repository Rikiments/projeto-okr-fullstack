package br.dev.rafael.okr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Initiative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Double porcentagemConclusao;

    @ElementCollection
    @CollectionTable(name = "initiative_tasks", joinColumns = @JoinColumn(name = "initiative_id"))
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "key_result_id")
    @JsonBackReference
    private KeyResult keyResult;
}
