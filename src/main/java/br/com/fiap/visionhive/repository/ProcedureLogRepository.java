package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.ProcedureLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcedureLogRepository extends MongoRepository<ProcedureLog, String> {
}
