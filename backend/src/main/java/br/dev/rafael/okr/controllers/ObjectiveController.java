package br.dev.rafael.okr.controllers;

import br.dev.rafael.okr.entities.Objective;
import br.dev.rafael.okr.repositorios.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/objetivos")
@CrossOrigin(origins = "*")
public class ObjectiveController {
    @Autowired
    private ObjectiveRepository repository;

    @GetMapping
    public List<Objective> listar() {
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Objective> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Objective criar(@RequestBody Objective objetivo) {
        if (objetivo.getPorcentagemConclusao() == null) {
            objetivo.setPorcentagemConclusao(0.0);
        }
        return repository.save(objetivo);
    }

    @PutMapping("/{id}")
    public Objective atualizar(@PathVariable Long id, @RequestBody Objective novoObj) {
        Objective antigo = repository.findById(id).orElseThrow();
        antigo.setTitulo(novoObj.getTitulo());
        antigo.setDescricao(novoObj.getDescricao());
        antigo.setPorcentagemConclusao(novoObj.getPorcentagemConclusao());
        return repository.save(antigo);
    }
    
    @PutMapping("/{id}/porcentagem")
    public Objective atualizarPorcentagem(@PathVariable Long id, @RequestBody Double porcentagem) {
        Objective obj = repository.findById(id).orElseThrow();
        obj.setPorcentagemConclusao(porcentagem);
        return repository.save(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}