package br.com.fiap.visionhive.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "procedure_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureLog {
    @Id
    private String id;
    private String procedureName;
    private String username;
    private String State;
    private LocalDateTime timestamp;

    public ProcedureLog(String procedureName, String username, String state) {
        this.procedureName = procedureName;
        this.username = username;
        this.State = state;
        this.timestamp = LocalDateTime.now();
    }
}
