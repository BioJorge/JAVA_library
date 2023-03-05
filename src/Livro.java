public class Livro {
    private int id;
    private String titulo;
    private int ultimo_locador;
    private int locador_atual;

    //Constructor
    public Livro(int id, String titulo){
        this.id = id;
        this.titulo = titulo;
        this.locador_atual = -99;
        this.ultimo_locador = -99;
    }
     //Methods


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUltimo_locador() {
        return ultimo_locador;
    }

    public void setUltimo_locador(int ultimo_locador) {
        this.ultimo_locador = ultimo_locador;
    }

    public int getLocador_atual() {
        return locador_atual;
    }

    public void setLocador_atual(int locador_atual) {
        this.locador_atual = locador_atual;
    }

    public void show(){
        String ultimo_locador = ""; String locador_atual = "";
        if (this.ultimo_locador == -99){
            ultimo_locador = "...";
        }else {
            ultimo_locador = Integer.toString(this.ultimo_locador);
        }
        if (this.locador_atual == -99){
            locador_atual = "Livro disponível";
        }else {
            locador_atual = Integer.toString(this.locador_atual);
        }

        System.out.print(this.id+" - Título: (");
        System.out.print(this.titulo+") | Último Locador: (");
        System.out.print(ultimo_locador+") | Locador atual : (");
        System.out.print(locador_atual+").\n");
    }
}
