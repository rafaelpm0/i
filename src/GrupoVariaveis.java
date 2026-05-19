import java.util.ArrayList;
import java.util.HashMap;

public class GrupoVariaveis {
    ArrayList<VariavelFuzzy> listaDeVariaveis;

    public GrupoVariaveis() {
        this.listaDeVariaveis = new ArrayList<>();
    }

    public void adicionarVariavel(VariavelFuzzy variavel) {
        this.listaDeVariaveis.add(variavel);
    }

    public void calcularFuzzificacao(float valorReal, HashMap<String, Float> mapaResultado) {
        for (int i = 0; i < listaDeVariaveis.size(); i++) {
            VariavelFuzzy var = listaDeVariaveis.get(i);
            float grau = var.calcularPertinencia(valorReal);
            
            mapaResultado.put(var.nome, grau);
        }
    }
}