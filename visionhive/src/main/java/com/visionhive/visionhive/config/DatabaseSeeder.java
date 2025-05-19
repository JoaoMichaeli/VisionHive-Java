package com.visionhive.visionhive.config;

import com.visionhive.visionhive.model.Branch;
import com.visionhive.visionhive.model.Motorcycle;
import com.visionhive.visionhive.model.MotorcycleGroup;
import com.visionhive.visionhive.model.Patio;
import com.visionhive.visionhive.repository.BranchRepository;
import com.visionhive.visionhive.repository.MotorcycleRepository;
import com.visionhive.visionhive.repository.PatioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DatabaseSeeder {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private PatioRepository patioRepository;

    @PostConstruct
    public void init(){

        Branch filialA = Branch.builder()
                .nome("Filial Central")
                .bairro("Butantã")
                .cnpj("96895689000139")
                .build();
        branchRepository.save(filialA);

        Patio patioEmplacamento = Patio.builder()
                .nome("Pátio de emplacamento")
                .branch(filialA)
                .build();
        patioRepository.save(patioEmplacamento);

        Motorcycle motoA = Motorcycle.builder()
                .placa("ABC1234")
                .chassi("9BWZZZ377VT0042245")
                .numeracaoMotor("MTR12345678")
                .motorcycleModels(Collections.singletonList(MotorcycleGroup.MottuSport))
                .patio(patioEmplacamento)
                .build();
        motorcycleRepository.save(motoA);


        Branch filialB = Branch.builder()
                .nome("Filial Norte")
                .bairro("Santana")
                .cnpj("12345678000100")
                .build();
        branchRepository.save(filialB);

        Patio patioRecebimento = Patio.builder()
                .nome("Pátio de recebimento")
                .branch(filialB)
                .build();
        patioRepository.save(patioRecebimento);

        Patio patioInspecao = Patio.builder()
                .nome("Pátio de inspeção")
                .branch(filialB)
                .build();
        patioRepository.save(patioInspecao);

        Motorcycle motoB = Motorcycle.builder()
                .placa("DEF5678")
                .chassi("9BWZZZ377VT0042255")
                .numeracaoMotor("MTR87654321")
                .motorcycleModels(Collections.singletonList(MotorcycleGroup.MottuPop))
                .patio(patioRecebimento)
                .build();
        motorcycleRepository.save(motoB);

        Motorcycle motoC = Motorcycle.builder()
                .placa("GHI9012")
                .chassi("9BWZZZ377VT0042266")
                .numeracaoMotor("MTR11223344")
                .motorcycleModels(Collections.singletonList(MotorcycleGroup.MottuPop))
                .patio(patioInspecao)
                .build();
        motorcycleRepository.save(motoC);


        Branch filialC = Branch.builder()
                .nome("Filial Sul")
                .bairro("Campo Limpo")
                .cnpj("10293847560001")
                .build();
        branchRepository.save(filialC);

        Patio patioEntrega = Patio.builder()
                .nome("Pátio de entrega")
                .branch(filialC)
                .build();
        patioRepository.save(patioEntrega);

        Motorcycle motoD = Motorcycle.builder()
                .placa("JKL3456")
                .chassi("9BWZZZ377VT0042277")
                .numeracaoMotor("MTR55667788")
                .motorcycleModels(Collections.singletonList(MotorcycleGroup.MottuE))
                .patio(patioEntrega)
                .build();
        motorcycleRepository.save(motoD);
    }
}

