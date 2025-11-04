package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    public Map<Long, Long> countMotorcyclesByBranch() {
        return motorcycleRepository.countMotorcyclesByBranch()
                .stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]
                ));
    }

    public Map<Long, Long> countMotorcyclesByPatio() {
        return motorcycleRepository.countMotorcyclesByPatio()
                .stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (Long) result[1]
                ));
    }

    public void save(Motorcycle motorcycle) {
        motorcycleRepository.save(motorcycle);
    }

    public Motorcycle findById(Long id) {
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motocicleta não encontrada com ID: " + id));
    }

    public Optional<Object> findByPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return Optional.empty();
        }
        return motorcycleRepository.findByPlaca(placa.toUpperCase().trim());
    }

    public void deleteById(Long id) {
        if (!motorcycleRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: motocicleta não encontrada com ID: " + id);
        }
        motorcycleRepository.deleteById(id);
    }

}
