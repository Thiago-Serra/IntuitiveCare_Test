# 🏥 API de Operadoras de plano de saúde

API REST para consulta de operadoras de plano saúde, desenvolvida em Java com Spring Boot.

## 🚀 Tecnologias
- Java 22
- Spring Boot 3.x
- Maven
- Vue.js (frontend opcional)

## 📋 Pré-requisitos
- JDK 18+
- Maven 3.8+
- MySql (ou outro banco, se aplicável)


## 📋 Diagrama da estrutura de arquivos detalhadas
📦 IntuitiveCare
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java
│   │   │   ├── 📂 teste1e2
│   │   │   │   ├── 📄 AnsPo.java             # Scraping e download 
│   │   │   │   ├── 📄 BaseScraper.java       # Modelo para web scraping
│   │   │   │   ├── 📄 Exec.java              # Executor
│   │   │   │   ├── 📄 PdfDataProcessor.java  # Processador
│   │   │   │   └── 📂 teste3
│   │   │   │       ├── 📄 FileDownloader.java          # Downloader dos ZIPs e CSV
|   |   |   |       |                                                    
│   │   │   │       └── 📄 OperadoraDataImporter.java   # Criador de tabelas e queries analiticas
│   │   │   └── 📂 teste4
│   │   │       ├── 📄 ApiApplication.java    # Spring Boot Main
│   │   │       ├── 📄 Operadora.java         # Modelo
│   │   │       ├── 📄 OperadoraController.java # REST
│   │   │       └── 📄 OperadoraRepository.java # Dados
│   │   └── 📂 resources
│   │       ├── 📄 operadoras.csv           # Fonte de dados
│   │       └── 📂 static
│   │           └── 📄 Front_Vue.html       # Frontend
├── 📂 target                              # Build
└── 📄 pom.xml                             # Dependências