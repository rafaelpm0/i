import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        
        GrupoVariaveis grupoNota = new GrupoVariaveis();
        grupoNota.adicionarVariavel(new VariavelFuzzy("Ruim", 0, 0, 1.5f, 4.5f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Media", 3.5f, 5.5f, 5.5f, 7.5f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Boa", 6.5f, 8.5f, 10, 10));
        
        GrupoVariaveis grupoPopularidade = new GrupoVariaveis();
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("BaixaPop", 0, 0, 100, 1000));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("MediaPop", 500, 2500, 5000, 10000));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("AltaPop", 7000, 15000, 1000000, 1000000));

        try {
            File arquivo = new File("movie_dataset.csv");
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            String linha = leitor.readLine(); 
            
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (dados.length < 21) continue;

                float nota = Float.parseFloat(dados[19]);
                float votos = Float.parseFloat(dados[20]);
                String titulo = dados[18];

                HashMap<String, Float> pertinencias = new HashMap<>();
                
                grupoNota.calcularFuzzificacao(nota, pertinencias);
                grupoPopularidade.calcularFuzzificacao(votos, pertinencias);
                
                HashMap<String, Float> conclusoes = new HashMap<>();
                
                aplicarRegraE(pertinencias, "Boa", "AltaPop", "AS", conclusoes);
                aplicarRegraE(pertinencias, "Boa", "MediaPop", "AS", conclusoes);
                
                float valMedia = pertinencias.getOrDefault("Media", 0.0f);
                conclusoes.put("MS", Math.max(conclusoes.getOrDefault("MS", 0.0f), valMedia));
                
                float valRuim = pertinencias.getOrDefault("Ruim", 0.0f);
                conclusoes.put("BS", Math.max(conclusoes.getOrDefault("BS", 0.0f), valRuim));
                
                float pesoBS = 1.8f;
                float pesoMS = 5.2f; 
                float pesoAS = 9.2f;
                
                float vBS = conclusoes.getOrDefault("BS", 0.0f);
                float vMS = conclusoes.getOrDefault("MS", 0.0f);
                float vAS = conclusoes.getOrDefault("AS", 0.0f);
                
                float dividendo = (vBS * pesoBS) + (vMS * pesoMS) + (vAS * pesoAS);
                float divisor = vBS + vMS + vAS;
                
                float scoreFinal = 0;
                if (divisor != 0) {
                    scoreFinal = dividendo / divisor;
                }
                
                if (scoreFinal > 0) {
                    System.out.printf("Filme: %-45s | Score: %.2f%n", titulo, scoreFinal);
                }
            }
            leitor.close();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void aplicarRegraE(HashMap<String, Float> pertinencias, String var1, String var2, String resultado, HashMap<String, Float> conclusoes) {
        float p1 = pertinencias.getOrDefault(var1, 0.0f);
        float p2 = pertinencias.getOrDefault(var2, 0.0f);
        float valorResultante = Math.min(p1, p2);
        
        float atual = conclusoes.getOrDefault(resultado, 0.0f);
        conclusoes.put(resultado, Math.max(valorResultante, atual));
    }
}