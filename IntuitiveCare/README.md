# 🏥 Testes de nivelamento IntuitiveCare

Testes que consistem em manipulação de dados, web scraping e desenvolvimento e testes de API

## 🚀 Tecnologias
- Java 22
- Spring Boot 3.x
- Maven
- Vue.js (frontend opcional)

## 📋 Pré-requisitos
- JDK 18+
- Maven 3.8+
- MySql 


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
