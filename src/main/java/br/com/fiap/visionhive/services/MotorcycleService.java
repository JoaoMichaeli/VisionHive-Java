package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    public void save(Motorcycle motorcycle) {
        motorcycleRepository.save(motorcycle);
    }

    public Motorcycle findById(Long id) {
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motocicleta não encontrada"));
    }

    public void deleteById(Long id) {
        motorcycleRepository.deleteById(id);
    }
}
