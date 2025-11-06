ALTER TABLE buzzer_log
ALTER COLUMN sucesso TYPE BOOLEAN USING CASE
    WHEN sucesso = '1' THEN TRUE
    WHEN sucesso = '0' THEN FALSE
    WHEN sucesso = 'T' THEN TRUE
    WHEN sucesso = 'F' THEN FALSE
    ELSE NULL
END;