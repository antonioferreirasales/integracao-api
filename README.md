# Integração API — Exportação para Fortes Fiscal

Aplicação desktop em **Java 17 e JavaFX** para consultar dados fiscais da API da VR e exportar um arquivo de integração no **layout Fortes Fiscal 175**. O projeto utiliza o consumo de API HTTP de uma aplicação web, modela documentos fiscais com essas informações e gera um arquivo com layout através de uma interface gráfica.

> **Status:** projeto de portfólio que não chegou a produção. O repositório contém fluxos implementados e funcionalidades em desenvolvimento; não representa uma solução fiscal homologada ou pronta para uso operacional.

## Contexto e objetivo

A proposta é facilitar a transferência de informações entre um sistema ERP Web e o sistema fiscal de contabilidade, concentrando a seleção de loja, período e registros em uma única ferramenta. A aplicação consulta notas e seus detalhes, organiza participantes e produtos e converte esses dados para o formato válido de exportação de acordo com layout oficial da Fortes.

## Funcionalidades implementadas

- Interface gráfica com biblioteca moderna de componentes.
- Cadastro, edição e exclusão de configurações de lojas em SQLite, incluindo token de acesso, códigos de empresa e estabelecimento e parâmetros fiscais.
- Autenticação por token e consulta de notas fiscais de recebimento e emissão, com busca dos respectivos detalhes.
- Seleção de loja, intervalo de datas, tipo de data e diretório de destino pela interface.
- Rotinas de exportação de participantes, produtos, unidades de medida, naturezas de operação e notas de entrada e saída.
- Geração de cabeçalho e encerramento com contagem de registros no layout 175.
- Persistência de preferências em arquivo `.properties` e infraestrutura de logs com SLF4J.

O arquivo gerado segue o nome `Fortes_loja<ID>.fs` e utiliza codificação `windows-1252`.

## Tecnologias

| Tecnologia                     | Uso no projeto                                |
| ------------------------------ | --------------------------------------------- |
| Java 17                        | Linguagem e plataforma de execução            |
| JavaFX 17 e FXML               | Interface desktop e definição das telas       |
| AtlantaFX, ControlsFX e Ikonli | Tema visual, componentes e ícones             |
| Java HTTP Client               | Requisições de autenticação e consulta        |
| Jackson                        | Conversão de JSON em DTOs, incluindo datas    |
| SQLite e JDBC                  | Persistência local das configurações de lojas |
| Lombok                         | Redução de código repetitivo nos modelos      |
| SLF4J                          | Registro de eventos e erros                   |
| Maven e Maven Wrapper          | Dependências, compilação e execução           |

## Organização e fluxo

```text
Interface JavaFX
      |
      v
Controlador de exportação
      |
      v
Serviço Fortes 175 <--- Configurações de lojas (SQLite)
      |
      +-- Autenticação e consultas à API VR
      +-- Conversão de JSON para DTOs
      +-- Transformação em registros fiscais
      |
      v
Fortes_loja<ID>.fs
```

O código está organizado sob `src/main/java/br/com/vrfortaleza/integracaoapi`:

| Diretório           | Responsabilidade                                        |
| ------------------- | ------------------------------------------------------- |
| `api/`              | Cliente HTTP, endpoints, serviços de consulta e DTOs    |
| `pages/`            | Controladores e componentes da interface                |
| `controller/`       | Entrada do fluxo de exportação                          |
| `services/`         | Orquestração e transformação dos dados para exportação  |
| `dao/`              | Acesso às configurações e apoio às regras fiscais       |
| `database/`         | Conexão SQLite, esquema e operações de persistência     |
| `vo/`               | Objetos de configuração, enumerações e registros Fortes |
| `config/` e `util/` | Propriedades, logs, arquivos e formatação               |

As telas FXML e os recursos visuais ficam em `src/main/resources`.

## Executando localmente

### Pré-requisitos

- JDK 17, com `JAVA_HOME` configurado.
- Acesso à internet para baixar as dependências do Maven.
- Ambiente gráfico para executar o JavaFX.
- Acesso autorizado à API VR e token de uma loja para realizar a exportação.

O código foi desenvolvido e testado em Windows.

### Compilação e inicialização

Na raiz do projeto, pelo PowerShell:

```powershell
# Compilar o projeto
.\mvnw.cmd clean compile

# Abrir a aplicação
.\mvnw.cmd javafx:run
```

Com Maven instalado, os comandos equivalentes são `mvn clean compile` e `mvn javafx:run`.

### Configuração e uso

1. Abra **Sistema → Configuração → API**, informe o **Config-Token** e clique em **Salvar configuração da API**. Depois, na aba **Loja**, crie uma loja.
2. Preencha o nome, o token de acesso, os códigos de empresa e estabelecimento e os parâmetros fiscais. Salve a configuração.
3. Na tela de exportação, selecione a loja, o período, o tipo de data e o layout **FISCAL 175**.
4. Escolha os registros e uma pasta de destino.
5. Clique em exportar e aguarde o arquivo ser gerado.

O endereço da API está definido em `api/URL.java` e atualmente aponta para um ambiente de staging.

As configurações de lojas são gravadas em `db/integracao.db`, sob o diretório calculado por `Folder` e `EnderecosDiretorio`. As preferências ficam em `integracao-api.properties`.

O **Config-Token** é uma configuração global, diferente do token de autenticação de cada loja. Ele é salvo localmente em `api.properties`, na chave `api.configToken`, dentro do mesmo diretório da aplicação.

## Competências demonstradas

- **Integração entre sistemas:** autenticação, requisições HTTP e desserialização de respostas em modelos Java.
- **Transformação de dados:** conversão de documentos da API para registros de um layout de integração, com formatação de campos, datas e valores.
- **Modelagem de domínio:** uso de DTOs, objetos de valor e enumerações para representar documentos e conceitos fiscais.
- **Desenvolvimento desktop:** composição de telas com FXML, componentes reutilizáveis e processamento em segundo plano.
- **Persistência relacional:** operações de cadastro e consulta com JDBC, SQLite e instruções parametrizadas.

## Limitações e próximos passos

O projeto não foi utilizado em produção. Há regras e campos a serem implementados, valores provisórios e pontos que precisam de validação extra.

- A interface apresenta opções de cupom fiscal eletrônico, instrumentos de pagamento, cartão, inventário e estoque escriturado, mas o fluxo de `Fortes175Service` ainda não implementa essas exportações.
- As rotinas de consulta precisam de revisão da paginação e de testes com grandes volumes de dados.
- O projeto ainda não conta com testes automatizados.
- Faltam testes de integração e validação dos arquivos gerados no sistema Fortes.
