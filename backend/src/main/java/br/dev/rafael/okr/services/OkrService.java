package br.dev.rafael.okr.services;

import br.dev.rafael.okr.entities.Initiative;
import br.dev.rafael.okr.entities.KeyResult;
import br.dev.rafael.okr.entities.Objective;
import br.dev.rafael.okr.repositorios.InitiativeRepository;
import br.dev.rafael.okr.repositorios.KeyResultRepository;
import br.dev.rafael.okr.repositorios.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OkrService {
    @Autowired
    private InitiativeRepository initiativeRepository;

    @Autowired
    private KeyResultRepository keyResultRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Transactional
public void atualizarPorcentagemKr(Long krId) {
    KeyResult kr = keyResultRepository.findById(krId).orElseThrow();
    List<Initiative> iniciativas = kr.getIniciativas();

    if (iniciativas == null || iniciativas.isEmpty()) {
        kr.setPorcentagemConclusao(0.0);
    } else {
        double mediaPorcentagem = iniciativas.stream()
                .map(Initiative::getPorcentagemConclusao)
                .map(porcentagem -> porcentagem == null ? 0.0 : porcentagem) 
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        kr.setPorcentagemConclusao(mediaPorcentagem);
    }

    keyResultRepository.save(kr);

    if (kr.getObjective() != null) {
        atualizarPorcentagemObjetivo(kr.getObjective().getId());
    }
}
    @Transactional
public void atualizarPorcentagemObjetivo(Long objectiveId) {
    Objective objetivo = objectiveRepository.findById(objectiveId).orElseThrow();
    List<KeyResult> keyResults = objetivo.getKeyResults();

    if (keyResults == null || keyResults.isEmpty()) {
        objetivo.setPorcentagemConclusao(0.0);
    } else {
        double mediaPorcentagem = keyResults.stream()
                .map(KeyResult::getPorcentagemConclusao)
                .map(porcentagem -> porcentagem == null ? 0.0 : porcentagem) 
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        objetivo.setPorcentagemConclusao(mediaPorcentagem);
    }

    objectiveRepository.save(objetivo);
}


    @Transactional
    public Initiative atualizarPorcentagemIniciativa(Long initiativeId, Double porcentagem) {
        Initiative iniciativa = initiativeRepository.findById(initiativeId).orElseThrow();
        iniciativa.setPorcentagemConclusao(porcentagem);
        initiativeRepository.save(iniciativa);
        
        if (iniciativa.getKeyResult() != null) {
            atualizarPorcentagemKr(iniciativa.getKeyResult().getId());
        }
        
        return iniciativa;
    }
}