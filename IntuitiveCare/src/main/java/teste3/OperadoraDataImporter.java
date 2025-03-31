package teste3;

import java.sql.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import java.sql.*;

public class OperadoraDataImporter {

    private static final String URL = "jdbc:mysql://localhost:3306/db_intuitivecare";
    private static final String USER = "root";
    private static final String PASSWORD = "MoneyMan!12345";

   
    public static void criarTabelas() {
        String sql = "CREATE TABLE IF NOT EXISTS operadoras ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nome VARCHAR(500), "
                    + "cnpj VARCHAR(20) UNIQUE, "
                    + "codigo_operadora INT, "
                    + "razao_social VARCHAR(255), "
                    + "nome_fantasia VARCHAR(255), "
                    + "modalidade VARCHAR(255), "
                    + "logradouro VARCHAR(255), "
                    + "numero VARCHAR(20), "
                    + "complemento VARCHAR(255), "
                    + "bairro VARCHAR(255), "
                    + "cidade VARCHAR(255), "
                    + "uf VARCHAR(2), "
                    + "cep VARCHAR(10), "
                    + "ddd VARCHAR(5), "
                    + "telefone VARCHAR(15), "
                    + "fax VARCHAR(15), "
                    + "endereco_eletronico VARCHAR(255), "
                    + "representante VARCHAR(255), "
                    + "cargo_representante VARCHAR(255), "
                    + "regiao_comercializacao INT, "
                    + "data_registro_ans DATE"
                    + ");";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'operadoras' criada ou atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static void importarDadosCSV(String caminhoArquivo) {
        String sql = "LOAD DATA INFILE '" + caminhoArquivo + "operadoras.csv' "
                + "INTO TABLE operadoras "
                + "FIELDS TERMINATED BY ',' "
                + "ENCLOSED BY '\"' "
                + "LINES TERMINATED BY '\\r\\n' "
                + "IGNORE 1 LINES "
                + "(nome, cnpj, codigo_operadora, razao_social, nome_fantasia, modalidade, "
                + "logradouro, numero, complemento, bairro, cidade, uf, cep, ddd, telefone, "
                + "fax, endereco_eletronico, representante, cargo_representante, regiao_comercializacao, data_registro_ans);";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Dados importados com sucesso para a tabela 'operadoras'!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void top10DespesasUltimoTrimestre() {
        String sql = "SELECT o.nome, SUM(dc.valor) AS total_despesas "
                   + "FROM demonstracoes_contabeis dc "
                   + "JOIN operadoras o ON dc.operadora_id = o.id "
                   + "WHERE dc.tipo_despesa = 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR' "
                   + "AND dc.ano = YEAR(CURDATE()) "
                   + "AND dc.trimestre = QUARTER(CURDATE()) "
                   + "GROUP BY o.id, o.nome "
                   + "ORDER BY total_despesas DESC "
                   + "LIMIT 10;";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n🔹 TOP 10 Operadoras com Maiores Despesas no Último Trimestre:");
            while (rs.next()) {
                String nome = rs.getString("nome");
                double totalDespesas = rs.getDouble("total_despesas");
                System.out.printf("%-50s | R$ %,15.2f\n", nome, totalDespesas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void top10DespesasUltimoAno() {
        String sql = "SELECT o.nome, SUM(dc.valor) AS total_despesas "
                   + "FROM demonstracoes_contabeis dc "
                   + "JOIN operadoras o ON dc.operadora_id = o.id "
                   + "WHERE dc.tipo_despesa = 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR' "
                   + "AND dc.ano = YEAR(CURDATE()) - 1 "
                   + "GROUP BY o.id, o.nome "
                   + "ORDER BY total_despesas DESC "
                   + "LIMIT 10;";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n🔹 TOP 10 Operadoras com Maiores Despesas no Último Ano:");
            while (rs.next()) {
                String nome = rs.getString("nome");
                double totalDespesas = rs.getDouble("total_despesas");
                System.out.printf("%-50s | R$ %,15.2f\n", nome, totalDespesas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        
    	
        criarTabelas();

        // importa os dados CSV na pasta de uploads do mysql para a leitura de dados
        importarDadosCSV("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\");
        
        top10DespesasUltimoTrimestre();
        top10DespesasUltimoAno();
    }
}
