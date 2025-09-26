package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.model.Patio;
import br.com.fiap.visionhive.repository.PatioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatioService {

    private final PatioRepository patioRepository;

    public List<Patio> findAllPatios() {
        return patioRepository.findAll();
    }

    public void save(Patio patio) {
        patioRepository.save(patio);
    }

    public Patio findById(Long id) {
        return patioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));
    }

}
