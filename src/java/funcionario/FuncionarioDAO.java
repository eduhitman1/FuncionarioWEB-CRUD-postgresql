package funcionario;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class FuncionarioDAO {
   // final private String URL = "jdbc:postgresql://10.70.132.62/bd";
    final private String URL = "jdbc:postgresql://localhost:5432/bd";
    final private String USER = "postgres";
    final private String PASSWORD = "eduhit00";
    private Connection con;
    private PreparedStatement st;
    private ResultSet rs;
    

    public FuncionarioDAO() throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão sucesso!!");
        } catch (SQLException e) {
            System.out.println("Falha Conexão");
        }
    }

    public void desconectar() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Falha ao fechar!");
        }
    }

    public Boolean inserir(Funcionario f, PrintWriter out) throws SQLException {
        try {
            String comando = "INSERT INTO funcionario (nome,cargo,salario) VALUES(?,?,?)";
            st = con.prepareStatement(comando);
            st.setString(1, f.getNome());
            st.setString(2, f.getCargo());
            st.setFloat(3, f.getSalario());
            st.execute();
            return true;
        } catch (SQLException e) {
            out.println(e.toString());
        }
        return false;
    }
    
    public Boolean atualizar(Funcionario f) throws SQLException {
        try {
            String comando = "UPDATE funcionario SET salario = ? WHERE id = ?";
            st = con.prepareStatement(comando);
            st.setFloat(1, f.getSalario());
            st.setInt(2, f.getId());
            st.execute();
            return true;
        } catch (SQLException e) {
           
        }
        return false;
    }
    
    public Boolean excluir(int id) throws SQLException {
        try {
            String comando = "DELETE FROM funcionario WHERE id = ?";
            st = con.prepareStatement(comando);
            st.setInt(1, id);
            st.execute();
            return true;
        } catch (SQLException e) {
           
        }
        return false;
    }

    public Funcionario consultarID(int id, PrintWriter out) {
        Funcionario func = new Funcionario();
        func.setId(0);
        
        try {
            String comando = "SELECT * FROM funcionario WHERE id=" + id;
            st = con.prepareStatement(comando);
            rs = st.executeQuery();
            if (rs.next()) {
                func.setId(id);
                func.setNome(rs.getString("nome"));
                func.setCargo(rs.getString("cargo"));
                func.setSalario(rs.getFloat("salario"));
                return func;
            }
        } catch (SQLException e) {
            out.println(e.toString());
        }
        
        return func;
    }

   public ArrayList listar(){
       try {
           String comando="SELECT * FROM funcionario ORDER BY id DESC";
           ArrayList<Funcionario> lista=new ArrayList<>();
           st=con.prepareStatement(comando);
           rs=st.executeQuery();
           while(rs.next()){
               Funcionario f=new Funcionario();
               f.setId(rs.getInt("id"));
               f.setNome(rs.getString("nome"));
               f.setCargo(rs.getString("cargo"));
               f.setSalario(rs.getFloat("salario"));
               lista.add(f);
           }
           return lista;
       } catch (SQLException e) {
           System.out.println("Falha ao listar"+e.getMessage());
       }
      return null;
   }   
}
