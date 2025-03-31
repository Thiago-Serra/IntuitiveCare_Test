package teste4;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;



@Repository
public class OperadoraRepository {
    private static List<Operadora> operadoras = new ArrayList<>();

    @PostConstruct
    public void carregarDadosCSV() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("operadoras.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            
            String linha;
            boolean cabecalho = true;
            
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }

                String[] dados = linha.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                Operadora op = new Operadora();
                op.setRegistroAns(dados[0].replace("\"", ""));
                op.setCnpj(dados[1].replace("\"", ""));
                op.setRazaoSocial(dados[2].replace("\"", ""));
                op.setNomeFantasia(dados[3].replace("\"", ""));
                op.setModalidade(dados[4].replace("\"", ""));

                operadoras.add(op);
            }
            br.close();
            System.out.println("Carregadas " + operadoras.size() + " operadoras");
            
            System.out.println("Primeiras 3 operadoras carregadas:");
            operadoras.stream().limit(3).forEach(op -> 
                System.out.println(op.getRazaoSocial() + " | " + op.getRegistroAns()));     
            
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar CSV", e);
        }
    }
    
    public List<Operadora> buscarPorTexto(String termo) {
        System.out.println("\n--- Termo buscado: '" + termo + "' ---");
        
        return operadoras.stream()
            .peek(op -> System.out.println("Verificando: " + op.getRazaoSocial()))
            .filter(op -> {
                boolean match = (op.getRazaoSocial() != null && op.getRazaoSocial().toLowerCase().contains(termo.toLowerCase())) ||
                              (op.getRegistroAns() != null && op.getRegistroAns().contains(termo));
                if (match) System.out.println(">>> MATCH: " + op.getRazaoSocial());
                return match;
            })
            .collect(Collectors.toList());
    }

    // um getter pra lista completa
    public List<Operadora> getOperadoras() {
        return operadoras;
        }
    }


	
