package br.dev.rafael.okr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String meta;
    private Double porcentagemConclusao;

    @ManyToOne
    @JoinColumn(name = "objective_id")
    @JsonBackReference
    private Objective objective;

    @OrderBy("id ASC") 
    @OneToMany(mappedBy = "keyResult", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Initiative> iniciativas;
}