# 🚀 Vision Hive

**Vision Hive** é uma API RESTful desenvolvida para a empresa Mottu com o objetivo de facilitar o gerenciamento e localização de motocicletas nos pátios operacionais. A aplicação permite o cadastro de **Filiais (Branch)**, **Pátios (Patio)** e **Motocicletas (Motorcycle)**, associando motos aos seus respectivos pátios e filiais, com busca por placa, chassi ou número do motor.

Além disso, o sistema possui **controle de acesso baseado em roles**:
- **ADMIN:** acesso completo a todas as rotas e funcionalidades, incluindo criação de operadores e visualização de todos os links rápidos no footer.
- **OPERADOR:** acesso restrito às rotas de motocicletas (`/motorcycle` e `/motorcycle/{id}`), e ao perfil do usuário. O footer não exibe os links rápidos.

## 📌 Descrição do Projeto

Este projeto tem como objetivo automatizar e otimizar a gestão das motos nos pátios da Mottu utilizando microcontroladores ESP32 conectados via Wi-Fi, sensores físicos e um sistema web responsivo. A proposta visa garantir uma operação mais ágil, segura e escalável, eliminando o controle manual e aumentando a precisão na localização e no monitoramento dos veículos.

## 🎯 Objetivos

- Identificar rapidamente uma moto específica no pátio utilizando um alerta visual e/ou sonoro.
- Fornecer uma visualização **em tempo real** da chamada e do status de resposta da moto.
- Garantir escalabilidade para aplicação em mais de 100 filiais com diferentes layouts.
- Oferecer uma interface intuitiva, acessível por desktop e mobile.
- Integrar sensores IoT nas motos para coleta automatizada de dados e status.

## 🚨 Dor da Mottu

Com centenas de motos distribuídas em mais de 100 pátios no Brasil e no México, a Mottu enfrenta dificuldades operacionais para localizar rapidamente veículos específicos, gerando atrasos logísticos e desperdício de tempo da equipe.

## 💡 Nossa Solução

O **VisionHive** propõe o uso de dispositivos **ESP32** com sensores físicos e conexão Wi-Fi, fixados em cada moto. Através de uma **plataforma web integrada**, é possível acionar **alertas visuais ou sonoros remotamente**, permitindo a identificação **precisa e ágil** de qualquer moto no pátio — sem depender de busca manual.

## 🪪 Login para testes como admin

- Login:
  ```adminCM```
- Senha:
  ```admin123```

## 🪪 Login para testes como operador

- Login:
  ```operadorCM```
- Senha:
  ```operador123```

---

## 🔗 Rotas Disponíveis

### 🏢 Filial (Branch)
| Verbo | Rota                  | Descrição                        |
|-------|-----------------------|---------------------------------|
| GET   | `/branch`         | Lista todas as filiais           |
| GET   | `/branch/{id}`    | Detalha uma filial por ID        |
| POST  | `/branch`         | Cadastra uma nova filial         |
| PUT   | `/branch/{id}`    | Atualiza os dados da filial      |
| DELETE| `/branch/{id}`    | Remove uma filial existente      |

### 🅿️ Pátio (Patio)
| Verbo | Rota                  | Descrição                        |
|-------|-----------------------|---------------------------------|
| GET   | `/patio`          | Lista todos os pátios            |
| GET   | `/patio/{id}`     | Detalha um pátio por ID          |
| POST  | `/patio`          | Cadastra um novo pátio           |
| PUT   | `/patio/{id}`     | Atualiza os dados do pátio       |
| DELETE| `/patio/{id}`     | Remove um pátio existente        |

### 🛵 Motocicleta (Motorcycle)
| Verbo | Rota                     | Descrição                                      |
|-------|--------------------------|-----------------------------------------------|
| GET   | `/motorcycle`        | Lista todas as motocicletas cadastradas       |
| GET   | `/motorcycle/{id}`   | Detalha uma motocicleta por ID                 |
| GET   | `/motorcycle/search` | Busca por placa, chassi ou número do motor    |
| POST  | `/motorcycle`        | Cadastra uma nova motocicleta                  |
| PUT   | `/motorcycle/{id}`   | Atualiza os dados da motocicleta               |
| DELETE| `/motorcycle/{id}`   | Remove uma motocicleta existente               |

---

## 🛠 Tecnologias Utilizadas

- Java 17+
- Spring Boot (Web, Data JPA, Validation, Security)
- Banco de Dados H2 (para desenvolvimento)
- Lombok
- Swagger (OpenAPI) para documentação automática
- Maven para gerenciamento de dependências
- Thymeleaf para frontend
- TailwindCSS para estilos

---

## 🧪 Exemplos de Testes

### 🔹 Criar Filial

**POST /branch**

```json
{
  "nome": "Filial Central",
  "bairro": "Butantã",
  "cnpj": "96895689000139"
}
```

---

### 🔹 Criar Pátio

**POST /patio**

```json
{
  "nome": "Pátio de Emplacamento",
  "bairro": "Bairro",
  "cnpj": "Digitos cnpj",
  "branchId": "COLE_AQUI_O_ID_DA_FILIAL"
}
```

> Substitua o `branchId` pelo valor real retornado no POST de filial.

---

### 🔹 Criar Motocicleta

**POST /motorcycle**

```json
{
  "placa": "ABC1234",
  "chassi": "9BWZZZ377VT0042245",
  "numeracaoMotor": "MTR12345678",
  "motorcycleModels": ["MODELO_MOTO"],
  "situacao" "Situação da moto",
  "patioId": "COLE_AQUI_O_ID_DO_PATIO"
}
```

> Substitua o `patioId` pelo valor real retornado no POST de pátio.
> Substitua o `MODELO_MOTO` por algum modelo cadastrado, sendo eles: MottuSport, MottuE ou MottuPop.

---

### 🔹 Buscar Motocicleta por Placa

**GET /motorcycle/search?placa=ABC1234**

---

### 🔹 Detalhar Filial com Pátios e Motocicletas

**GET /branch/{id}**

**Resposta esperada:**

```json
{
  "id": 1,
  "nome": "Filial Central",
  "bairro": "Butantã",
  "cnpj": "96895689000139",
  "patios": [
    {
      "id": 1,
      "nome": "Pátio de Emplacamento",
    }
  ]
}
```
---

## 🔐 Controle de Acesso

### ADMIN
- Pode acessar todas as rotas: `/branch`, `/patio`, `/motorcycle`.
- Pode criar operadores.
- Footer exibe todos os links rápidos.
- Botão de "Voltar" em formulários redireciona para `/`.

### OPERADOR
- Acesso restrito a `/motorcycle` e `/motorcycle/{id}`.
- Footer não exibe links rápidos.
- Botão de "Voltar" em formulários redireciona para `/motorcycle`.

---

## 🚀 Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/visionhive.git
   ```

2. Entre na pasta do projeto:
   ```bash
   cd VisionHive-Java
   ```
   
3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a aplicação via navegador web:
   ```
   http://localhost:8080
   ```

6. Acesse a documentação Swagger para testar as rotas:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## 👥 Integrantes

| Nome                   | RM       |
|------------------------|----------|
| João Victor Michaeli   | RM555678 |
| Larissa Muniz          | RM557197 |
| Henrique Garcia        | RM558062 |

---

> Projeto acadêmico desenvolvido na FIAP para o Challenge 2025
