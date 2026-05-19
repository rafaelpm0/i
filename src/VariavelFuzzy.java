public class VariavelFuzzy {
    String nome;
    float baseEsquerda, topoEsquerdo, topoDireito, baseDireita;

    public VariavelFuzzy(String nome, float b1, float t1, float t2, float b2) {
        this.nome = nome;
        this.baseEsquerda = b1;
        this.topoEsquerdo = t1;
        this.topoDireito = t2;
        this.baseDireita = b2;
    }

    public float calcularPertinencia(float valor) {
        if (valor <= baseEsquerda || valor >= baseDireita) {
            return 0.0f;
        }
        
        if (valor >= topoEsquerdo && valor <= topoDireito) {
            return 1.0f;
        }
        
        if (valor > baseEsquerda && valor < topoEsquerdo) {
            return (valor - baseEsquerda) / (topoEsquerdo - baseEsquerda);
        }
        
        if (valor > topoDireito && valor < baseDireita) {
            return 1.0f - ((valor - topoDireito) / (baseDireita - topoDireito));
        }

        return 0.0f;
    }
}