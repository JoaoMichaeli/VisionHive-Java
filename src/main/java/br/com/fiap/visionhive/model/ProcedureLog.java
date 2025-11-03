package br.com.fiap.visionhive.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "procedure_logs")
public record ProcedureLog(
        @Id String id,
        String procedureName,
        String username,
        String jsonResponse,
        LocalDateTime timestamp
) {}
