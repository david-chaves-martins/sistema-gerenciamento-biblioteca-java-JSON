public class Funcionario extends Pessoa{
    String cargo;
    String matricula;

    public Funcionario(String nome, String cpf, int idade, String sobrenome, String email, String senha, String cargo) {
        super(nome, cpf, idade, sobrenome, email, senha);
        this.cargo = cargo;
        this. matricula = "MAT-" + this.getId();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
