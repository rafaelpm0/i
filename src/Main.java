import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;



public class Main {
    public static void main(String[] args) {

        GrupoVariaveis grupoNota = new GrupoVariaveis();
        grupoNota.adicionarVariavel(new VariavelFuzzy("MuitoRuim", 0, 0, 1.0f, 2.5f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Ruim", 1.5f, 3.0f, 3.5f, 4.5f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Media", 3.5f, 5.0f, 5.5f, 6.5f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Boa", 5.5f, 7.0f, 8.0f, 9.0f));
        grupoNota.adicionarVariavel(new VariavelFuzzy("Excelente", 8.0f, 9.5f, 10f, 10f));

        GrupoVariaveis grupoVotos = new GrupoVariaveis();
        grupoVotos.adicionarVariavel(new VariavelFuzzy("MuitoBaixaVT", 0, 0, 50, 300));
        grupoVotos.adicionarVariavel(new VariavelFuzzy("BaixaVT", 100, 500, 1000, 3000));
        grupoVotos.adicionarVariavel(new VariavelFuzzy("MediaVT", 2000, 5000, 8000, 15000));
        grupoVotos.adicionarVariavel(new VariavelFuzzy("AltaVT", 10000, 30000, 100000, 300000));
        grupoVotos.adicionarVariavel(new VariavelFuzzy("MuitoAltaVT", 200000, 500000, 1000000, 1000000));

        GrupoVariaveis grupoOrcamento = new GrupoVariaveis();
        grupoOrcamento.adicionarVariavel(new VariavelFuzzy("SemOrc", 0, 0, 1000000, 5000000));
        grupoOrcamento.adicionarVariavel(new VariavelFuzzy("BaixoORC", 3000000, 15000000, 30000000, 60000000));
        grupoOrcamento.adicionarVariavel(new VariavelFuzzy("MedioORC", 40000000, 90000000, 140000000, 200000000));
        grupoOrcamento.adicionarVariavel(new VariavelFuzzy("AltoORC", 150000000, 300000000, 450000000, 700000000));
        grupoOrcamento.adicionarVariavel(new VariavelFuzzy("Blockbuster", 500000000, 800000000, 1000000000, 1000000000));

        GrupoVariaveis grupoPopularidade = new GrupoVariaveis();
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("Invisivel", 0, 0, 1, 5));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("BaixaPOP", 3, 10, 20, 40));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("MediaPOP", 30, 60, 100, 200));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("AltaPOP", 150, 300, 500, 800));
        grupoPopularidade.adicionarVariavel(new VariavelFuzzy("Viral", 600, 800, 1000, 1000));

        try {
            File arquivo = new File("../movie_dataset.csv");
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            String linha = leitor.readLine();
            List<Filme> filmes = new ArrayList<>();
            Auxiliar aux = new Auxiliar();

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (dados.length < 24) continue;

                Filme filme = new Filme(
                        aux.toInt(dados[0]),
                        aux.toLong(dados[1]),
                        dados[2],
                        dados[3],
                        aux.toInt(dados[4]),
                        dados[5],
                        dados[6],
                        dados[7],
                        dados[8],
                        aux.toDouble(dados[9]),
                        dados[10],
                        dados[11],
                        dados[12],
                        aux.toLong(dados[13]),
                        aux.toDouble(dados[14]),
                        dados[15],
                        dados[16],
                        dados[17],
                        dados[18],
                        aux.toDouble(dados[19]),
                        aux.toInt(dados[20]),
                        dados[21],
                        dados[22],
                        dados[23]
                );

                filmes.add(filme);
                System.out.println(filme);
            }

            leitor.close();

            for (Filme filme : filmes) {

                float nota = (float) filme.vote_average;
                float votos = filme.vote_count;
                float orcamento = (float) filme.budget;
                float popularidade = (float) filme.popularity;
                String titulo = filme.title;

                HashMap<String, Float> pertinencias = new HashMap<>();

                grupoNota.calcularFuzzificacao(nota, pertinencias);
                grupoVotos.calcularFuzzificacao(votos, pertinencias);
                grupoOrcamento.calcularFuzzificacao(orcamento, pertinencias);
                grupoPopularidade.calcularFuzzificacao(popularidade, pertinencias);

                HashMap<String, Float> conclusoes = new HashMap<>();

                aplicarRegras(pertinencias, conclusoes);

                float pesoBS = 1.8f;
                float pesoMS = 5.2f;
                float pesoAS = 9.2f;

                float vBS = conclusoes.getOrDefault("BS", 0.0f);
                float vMS = conclusoes.getOrDefault("MS", 0.0f);
                float vAS = conclusoes.getOrDefault("AS", 0.0f);

                float scoreFinal = (vBS * pesoBS + vMS * pesoMS + vAS * pesoAS)
                        / (vBS + vMS + vAS + 0.0001f);

                System.out.printf(
                        "Filme: %-35s | Nota: %.1f | Votos: %.0f | Pop: %.2f | Orc: %.0f | Score: %.2f%n",
                        titulo,
                        nota,
                        votos,
                        popularidade,
                        orcamento,
                        scoreFinal
                );
            }

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

    private static void aplicarRegras(HashMap<String, Float> pertinencias, HashMap<String, Float> conclusoes) {
        aplicarRegraE(pertinencias, "Excelente", "AltaVT", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Excelente", "MuitoAltaVT", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Excelente", "AltaPOP", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Excelente", "Viral", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "AltaVT", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "AltaPOP", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "MedioORC", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "AltoORC", "AS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "MediaVT", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Boa", "MediaPOP", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "MediaVT", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "MediaPOP", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "MedioORC", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "AltoORC", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "BaixaVT", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Media", "BaixaPOP", "MS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "BaixaVT", "BS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "MuitoBaixaVT", "BS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "BaixaPOP", "BS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "Invisivel", "BS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "SemOrc", "BS", conclusoes);
        aplicarRegraE(pertinencias, "Ruim", "BaixoORC", "BS", conclusoes);
        aplicarRegraE(pertinencias, "MuitoRuim", "Qualquer", "BS", conclusoes);
        aplicarRegraE(pertinencias, "MuitoRuim", "BaixaVT", "BS", conclusoes);
        aplicarRegraE(pertinencias, "MuitoRuim", "BaixaPOP", "BS", conclusoes);
        aplicarRegraE(pertinencias, "MuitoRuim", "SemOrc", "BS", conclusoes);
    }
}