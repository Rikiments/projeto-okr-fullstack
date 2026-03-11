package br.dev.rafael.okr.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private Double porcentagemConclusao;

    @OrderBy("id ASC") 
    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<KeyResult> keyResults;
}