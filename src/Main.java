import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static Scanner input_texto = new Scanner(System.in);
    public static DecimalFormat df0 = new DecimalFormat("#");
    public static ArrayList<Colaborador> colaboradores = new ArrayList<Colaborador>();
    public static ArrayList<Livro> livros = new ArrayList<Livro>();

    public static ArrayList<Associado> associados = new ArrayList<Associado>();
    public static int user = -99, book = -99, associado = -99;
    public static boolean logged = false, turn_on = true;
    public static void main(String[] args) {
        int option = -99;

        colaboradores.add(new Colaborador("Jorge", "BioJorge", 111));
        colaboradores.add(new Colaborador("Matheus", "Desa", 222));

        associados.add(new Associado("Alexandre", 333));
        associados.add(new Associado("Rodolfo", 444));
        associados.add(new Associado("Pedro", 555));


        livros.add(new Livro(0, "HP-1"));
        livros.add(new Livro(1, "HP-2"));
        livros.add(new Livro(2, "HP-3"));
        livros.add(new Livro(3, "HP-4"));
        livros.add(new Livro(4, "HP-5"));
        livros.add(new Livro(5, "HP-6"));
        livros.add(new Livro(6, "HP-7"));

        do {
            do {
                limpa();
                login();
            }while(!logged);

            do {
                limpa();
                menuPrincipal();
                option = input.nextInt();

                switch (option){
                    case 1: alugarLivro(); break;
                    case 2: devolverLivro(); break;
                    case 3: buscarLivro(); break;
                    case 4: listarLivros(); break;
                    case 5: buscarAssociado(); break;
                    case 6: listarAssociados(); break;
                    case 0: sair(); break;
                    case 10: turn_off(); break;
                    default:
                        System.out.println("Opcao invalida!!"); break;
                }
            }while(option != 0 && option != 10);
        }while(turn_on);
    }

    public static void login(){
        boolean found = false;
        System.out.print("Login: ");
        String login = input_texto.nextLine();
        for (int i = 0; i < colaboradores.size(); i++) {
            if (colaboradores.get(i).getLogin().equalsIgnoreCase(login)){
                user = i;
                found = true;
                break;
            }
        }
        if (found){
            System.out.print("Senha: ");
            int senha = input.nextInt();
            if (colaboradores.get(user).getSenha() == senha){
                logged = true;
                System.out.println("Login efectuado com sucesso!");
                System.out.println("Bem vindo, "+colaboradores.get(user).getNome()+".");
                tecleEnter();
            }
            else {
                System.out.println("Senha incorreta!!");
                tecleEnter();
            }
        }else {
            System.out.println("Login does not exist");
            tecleEnter();
        }

    }
    public static void menuPrincipal(){
        System.out.println("====== Biblioteca ======\n");
        System.out.println("1 - Alugar Livro");
        System.out.println("2 - Devolver Livro");
        System.out.println("3 - Buscar Livro");
        System.out.println("4 - Listar todos os livros");

        System.out.println("\n5 - Buscar Associado");
        System.out.println("6 - Listar todos os associados");

        System.out.println("\n0 - Sair");
        System.out.println("\n10 - Encerrar app");

        System.out.print("\nOption: ");
    }

    public static void limpa(){
        for (int i = 0; i < 25; i++){
            System.out.println();
        }
    }

    public static void alugarLivro(){
        limpa();
        boolean found_book = false, booked = true, found_associate = false;
        int locador = -99;

        System.out.println("----- Alugar livro -----");
        System.out.print("ID do livro: ");
        int id = input.nextInt();

        for (int i = 0; i < livros.size(); i++){
            if (livros.get(i).getId() == id){
                book = i;
                found_book = true;
                                //botei livro disponivel como -99. Livros NÃO disponiveis teram o nif do locador
                if (livros.get(book).getLocador_atual() == -99){
                    locador = livros.get(book).getLocador_atual();
                    booked = false;
                }

                break;
            }
        }
        if (!found_book){
            System.out.println("Livro nao encontrado");
            tecleEnter();
        }

        if (found_book && !booked){
            System.out.print("NIF do associado: ");
            int nif = input.nextInt();
            for (int j = 0; j < associados.size(); j++){
                if (associados.get(j).getNif() == nif){
                    found_associate = true;
                    if (associados.get(j).getAlugados() >= 3){
                        System.out.println("Associado não pode alugar mais do que 3 livros por vez.");
                        System.out.println(associados.get(j).getNome()+" deve devolver um para poder alugar outro");
                        tecleEnter();
                    }else {
                        livros.get(book).setLocador_atual(nif);
                        String titulo = livros.get(id).getTitulo();
                        associados.get(j).alugar_um_livro(id);
                        associados.get(j).setAlugados(associados.get(j).getAlugados() + 1);

                        System.out.println(livros.get(book).getTitulo()+ " alugado para " + associados.get(j).getNome());
                        tecleEnter();
                        break;
                    }
                }
            }
            if (!found_associate){
                System.out.println("Associado não encontrado. Operação cancelada!");
                tecleEnter();
            }
        }
        if (found_book && booked){
            System.out.println("Livro já encontra-se alugado.");
            tecleEnter();
        }
    }
    public static void devolverLivro(){
        limpa();
        boolean found_book = false, booked = true;
        System.out.println("----- Devolver livro -----");
        System.out.print("ID do livro: ");
        int id = input.nextInt();

        for (int i = 0; i < livros.size(); i++){
            if (livros.get(i).getId() == id){
                book = i;
                found_book = true;
                break;
            }
        }
        if (found_book){
            if (livros.get(book).getLocador_atual() == -99){
                System.out.println("O livro não está requisitado no momento");
                tecleEnter();
            }
            else {
                int j = getAssociatePosition(livros.get(book).getLocador_atual());
                livros.get(book).setUltimo_locador(livros.get(book).getLocador_atual());
                livros.get(book).setLocador_atual(-99);
                associados.get(j).setAlugados(associados.get(j).getAlugados() - 1);

                System.out.println(livros.get(book).getTitulo() +" devolvido com sucesso");
                tecleEnter();
            }
        }else{
            System.out.println("Livro nao encontrado");
            tecleEnter();
        }
    }
    public static void buscarLivro(){
        limpa();
        boolean found_book = false;
        String locador = "";

        System.out.println("----- Buscar Livro -----");
        System.out.print("ID do livro: ");
        int id = input.nextInt();
        for (int i = 0; i < livros.size(); i++){
            if (livros.get(i).getId() == id){
                found_book = true;
                livros.get(i).show();
                tecleEnter();
                break;
            }
        }
        if (!found_book){
            System.out.println("Livro nao encontrado");
            tecleEnter();
        }

    }
    public static void listarLivros(){
        limpa();
        System.out.println("====== Livros da Biblioteca ======");
        for (Livro l: livros) {
            l.show();
        }
        tecleEnter();
    }
    public static void buscarAssociado(){
        limpa();
        boolean found_user = false;
        System.out.println("----- Buscar Associado -----");
        System.out.print("NIF do associado: ");
        int nif = input.nextInt();
        for (int j = 0; j < associados.size(); j++){
            if (associados.get(j).getNif() == nif){
                found_user = true;
                associados.get(j).show();
                tecleEnter();
                break;
            }
        }
        if (!found_user){
            System.out.println("Associado não encontrado. Operação cancelada!");
            tecleEnter();
        }

    }
    public static void listarAssociados(){
        limpa();
        System.out.println("====== Associados da Biblioteca ======\n");
        for (Associado a: associados) {
            a.show();
        }
        tecleEnter();
    }
    public static void sair(){
        System.out.println("A sair...");
        tecleEnter();
        user = -99;
        logged = false;
    }

    public static void turn_off(){
        System.out.println("Encerrando app");
        logged = false;
        turn_on = false;
    }
    public static boolean tecleEnter(){
        //Variaveis Locais
        String enter;

        //Executar
        System.out.print("\nTecle <ENTER> para continuar...");
        enter = input_texto.nextLine();
        return !enter.equalsIgnoreCase("x");
    }

    public static int getAssociatePosition(int nif){
        for (int j = 0; j < associados.size(); j++) {
            if (associados.get(j).getNif() == nif) {
                return j;
            }
        }
        return -99;
    }
}