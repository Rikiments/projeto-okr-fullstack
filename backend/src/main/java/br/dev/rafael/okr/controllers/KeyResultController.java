package br.dev.rafael.okr.controllers;

import br.dev.rafael.okr.entities.KeyResult;
import br.dev.rafael.okr.entities.Objective;
import br.dev.rafael.okr.repositorios.KeyResultRepository;
import br.dev.rafael.okr.repositorios.ObjectiveRepository;
import br.dev.rafael.okr.services.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/krs")
@CrossOrigin(origins = "*")
public class KeyResultController {
    @Autowired
    private KeyResultRepository krRepository;

    @Autowired
    private ObjectiveRepository objRepository;
    
    @Autowired
    private OkrService okrService;

    @GetMapping
    public List<KeyResult> listar() {
        return krRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<KeyResult> buscarPorId(@PathVariable Long id) {
        return krRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idObjetivo}")
    public KeyResult criar(@PathVariable Long idObjetivo, @RequestBody KeyResult kr) {
        Objective obj = objRepository.findById(idObjetivo).orElseThrow();
        kr.setObjective(obj);
        
        KeyResult saved = krRepository.save(kr);
        
        okrService.atualizarPorcentagemObjetivo(idObjetivo);
        
        return saved;
    }

    @PutMapping("/{id}")
    public KeyResult atualizar(@PathVariable Long id, @RequestBody KeyResult novoKr) {
        KeyResult antigo = krRepository.findById(id).orElseThrow();
        antigo.setDescricao(novoKr.getDescricao());
        antigo.setMeta(novoKr.getMeta());
        antigo.setPorcentagemConclusao(novoKr.getPorcentagemConclusao());
        
        krRepository.save(antigo);
        
        if (antigo.getObjective() != null) {
            okrService.atualizarPorcentagemObjetivo(antigo.getObjective().getId());
        }
        
        return antigo;
    }
    
    @PutMapping("/{id}/porcentagem")
    public KeyResult atualizarPorcentagem(@PathVariable Long id, @RequestBody Double porcentagem) {
        KeyResult kr = krRepository.findById(id).orElseThrow();
        kr.setPorcentagemConclusao(porcentagem);
        krRepository.save(kr);
        
        if (kr.getObjective() != null) {
            okrService.atualizarPorcentagemObjetivo(kr.getObjective().getId());
        }
        
        return kr;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        KeyResult kr = krRepository.findById(id).orElseThrow();
        Long objId = null;
        
        if (kr.getObjective() != null) {
            objId = kr.getObjective().getId();
        }
        
        krRepository.deleteById(id);
        
        if (objId != null) {
            okrService.atualizarPorcentagemObjetivo(objId);
        }
        
        return ResponseEntity.noContent().build();
    }
}