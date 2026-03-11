package br.dev.rafael.okr.controllers;

import br.dev.rafael.okr.entities.Initiative;
import br.dev.rafael.okr.entities.KeyResult;
import br.dev.rafael.okr.repositorios.InitiativeRepository;
import br.dev.rafael.okr.repositorios.KeyResultRepository;
import br.dev.rafael.okr.services.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iniciativas")
@CrossOrigin(origins = "*") 
public class InitiativeController {
    @Autowired
    private InitiativeRepository initiativeRepository;

    @Autowired
    private KeyResultRepository keyResultRepository;
    
    @Autowired
    private OkrService okrService;

    @GetMapping
    public List<Initiative> listar() {
        return initiativeRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Initiative> buscarPorId(@PathVariable Long id) {
        return initiativeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idKr}")
    public Initiative criar(@PathVariable Long idKr, @RequestBody Initiative iniciativa) {
        KeyResult kr = keyResultRepository.findById(idKr).orElseThrow();
        iniciativa.setKeyResult(kr);
        
        Initiative saved = initiativeRepository.save(iniciativa);
        
        okrService.atualizarPorcentagemKr(idKr);
        
        return saved;
    }

    @PatchMapping("/{id}/tasks/{index}")
public ResponseEntity<Initiative> atualizarStatusTask(@PathVariable Long id,
                                                      @PathVariable int index,
                                                      @RequestParam boolean concluida) {
    Initiative iniciativa = initiativeRepository.findById(id).orElseThrow();
    if (index >= 0 && index < iniciativa.getTasks().size()) {
        iniciativa.getTasks().get(index).setConcluida(concluida);
        initiativeRepository.save(iniciativa);
        return ResponseEntity.ok(iniciativa);
    } else {
        return ResponseEntity.badRequest().build();
    }
}


    @PutMapping("/{id}")
    public Initiative atualizar(@PathVariable Long id, @RequestBody Initiative nova) {
        Initiative antiga = initiativeRepository.findById(id).orElseThrow();
        antiga.setTitulo(nova.getTitulo());
        antiga.setDescricao(nova.getDescricao());
        antiga.setPorcentagemConclusao(nova.getPorcentagemConclusao());
        
        initiativeRepository.save(antiga);
        
        if (antiga.getKeyResult() != null) {
            okrService.atualizarPorcentagemKr(antiga.getKeyResult().getId());
        }
        
        return antiga;
    }
    
    @PutMapping("/{id}/porcentagem")
    public Initiative atualizarPorcentagem(@PathVariable Long id, @RequestBody Double porcentagem) {
        return okrService.atualizarPorcentagemIniciativa(id, porcentagem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Initiative iniciativa = initiativeRepository.findById(id).orElseThrow();
        Long krId = null;
        
        if (iniciativa.getKeyResult() != null) {
            krId = iniciativa.getKeyResult().getId();
        }
        
        initiativeRepository.deleteById(id);
        
        if (krId != null) {
            okrService.atualizarPorcentagemKr(krId);
        }
        
        return ResponseEntity.noContent().build();
    }
}