public class Livros {
    private static int contadorId = 1;
    private int id;
    private String titulo;
    private String autor;
    private String anoPublicacao;
    private boolean status;


    public Livros(String titulo, String autor, String anoPublicacao) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.status = true;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getAnoPublicacao() { return anoPublicacao; }

    public void setAnoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; }
}
