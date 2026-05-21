
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        
        String arquivo_filmes = "../movie_dataset.csv";

        try  {
            BufferedReader br = new BufferedReader(new FileReader(arquivo_filmes));
            br.readLine(); // Pula o cabeçalho
            String linha;

            List<Filme> filmes = new ArrayList<>();
            Auxiliar aux = new Auxiliar();
while ((linha = br.readLine()) != null) {

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
        } catch (IOException e) {
            e.printStackTrace();

        }

    }   

}
