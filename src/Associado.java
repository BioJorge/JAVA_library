import java.util.ArrayList;

public class Associado {
    private String nome;
    private int nif;
    //podias ter usado um arraylist de integer para representar os IDs dos livros,
    // nao precisaria entao do int alugados para contabilizar a qtd de livros
    private int[] livros_alugados = new int[3];
    public int alugados;

    //Constructor
    public Associado(String nome, int nif){
        this.nome = nome;
        this.nif = nif;
        this.alugados = 0;
    }

    //Methods
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public void alugar_um_livro(int id){
        livros_alugados[alugados] = id;
    }

    public int getAlugados() {
        return alugados;
    }

    public void setAlugados(int alugados) {
        this.alugados = alugados;
    }

    public void show(){
        System.out.print("Nome: "+ this.nome +" - NIF: (");
        System.out.print(this.nif +").\n");
        System.out.println("Livros atuais: ");
        for (int i = 0; i < alugados; i++){
            Main.livros.get(livros_alugados[i]).show();
        }
        System.out.println("----------------------------------");
    }
}
