import java.util.ArrayList;

public class Cliente extends Pessoa {
    private String carteirinha;
    private ArrayList<Livros> livrosEmprestados;

    public Cliente(String nome, String cpf, int idade, String sobrenome, String email, String senha) {
        super(nome, cpf, idade, sobrenome, email, senha);

        this.carteirinha = "CART-" + this.getId();

        this.livrosEmprestados = new ArrayList<>();
    }

    public String getCarteirinha() {
        return carteirinha;
    }

    public ArrayList<Livros> getLivrosEmprestados() {
        return livrosEmprestados;
    }
}