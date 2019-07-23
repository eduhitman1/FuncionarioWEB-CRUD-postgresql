import funcionario.Funcionario;
import funcionario.FuncionarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

public class Controle extends HttpServlet {

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
   response.setContentType("text/html;charset=UTF-8");
   try (PrintWriter out = response.getWriter()) {
        String erro = "";
        String tipoRequisicao = request.getParameter("system");
            if(!tipoRequisicao.isEmpty() && tipoRequisicao != null) {
       switch(tipoRequisicao) {
             case "index_select": {
                 String selectTipo = request.getParameter("select");
                 switch(selectTipo) {
                   
                    case "Listar": {
                    erro = "<table border=\"1\" style=\"width:50%\"><tr>"
                            + "<th>ID</th>"
                            + "<th>Nome</th>"
                            + "<th>Cargo</th>"
                            + "<th>Salario</th><th>Opções</th></tr>";
                            try {
                                FuncionarioDAO funcDAO = new FuncionarioDAO();
                                ArrayList<Funcionario> funcionarios = funcDAO.listar();
                                    for(Funcionario func : funcionarios) {
                                        erro += "<tr><td>"+func.getId()+
                                               "</td><td>"+func.getNome()+
                                                "</td><td>"+func.getCargo()+
                                                "</td><td>R$"+func.getSalario()+
                                                "</td><td><a href='./atualizar.jsp?id="+func.getId()+"'>Atualizar</a>"
                                                 + " | <a href='./excluir.jsp?id="+func.getId()+"'>Excluir</a></td></tr>";
                                               }
                                    erro += "</table>";
                                    
                                } catch (Exception e) {
                                    erro = e.toString();
                                }
                            }
                    break;
                            
                    case "Cadastrar": 
                                response.sendRedirect("./cadastrar.html");
                           
                    break;
                            
                    case "Pesquisar por ID": 
                                response.sendRedirect("./pesquisar.html");
                     break;
                        }
                    }
                    break;
                    
                    case "cadastrar":
                    {
                        String nome = request.getParameter("nome");
                        String cargo = request.getParameter("cargo");                     
                        float salario = Float.parseFloat(request.getParameter("salario"));
                        
                        Funcionario func = new Funcionario();
                        func.setNome(nome);
                        func.setCargo(cargo);
                        func.setSalario(salario);
                        
                        try {
                            FuncionarioDAO funcDAO = new FuncionarioDAO();
                            Boolean inserido = funcDAO.inserir(func, out);
                            
                            if(inserido) erro = "Funcionario inserido com sucesso!";
                            //else erro = "Falha ao inserir funcionario!";
                            
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,"erro ao inserir"+e);
                        }
                    }
                    break;
                    
                    case "pesquisar": {
                        try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        
                        if(id > 0) {
                            FuncionarioDAO funcDAO = new FuncionarioDAO();
                            Funcionario func = funcDAO.consultarID(id, out);
                            
                            if(func.getId() > 0) {
                                erro = "<b>Nome do Funcionario</b>: " + func.getNome()
                                        + "<br><b>Cargo</b>: " + func.getCargo()
                                        + "<br><b>Salario</b>: " + func.getSalario()
                                        + "<br><a href='./atualizar.jsp?id="+id+"'>Atualizar</a> | <a href='./excluir.jsp?id="+id+"'>Excluir</a>";
                            } else erro = "Retornou ID 0";
                        } else erro = "O ID precisa ser acima de 0!";
                        } catch (Exception e) { out.println(e.toString()); }
                    }
                    break;
                    
                    case "atualizar": {
                        try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        float salario = Float.parseFloat(request.getParameter("salario"));
                           if(id > 0) {
                            Funcionario f = new Funcionario();
                            f.setId(id);
                            f.setSalario(salario);
                            
                            FuncionarioDAO funcDAO = new FuncionarioDAO();
                            Boolean atualizado = funcDAO.atualizar(f);
                            
                            if(atualizado) {
                                erro = "Funcionario atualizado com sucesso!";
                            } else erro = "Ocorreu um erro ao atualizar o funcionario!";
                        } else erro = "O ID precisa ser acima de 0!";
                        } catch (Exception e) { out.println(e.toString()); }
                    }
                    break;
                    
                    case "excluir": {
                        try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        if(id > 0) {
                            FuncionarioDAO funcDAO = new FuncionarioDAO();
                            Boolean excluido = funcDAO.excluir(id);
                            
                            if(excluido) {
                                erro = "Funcionario deletado com sucesso!<br><br><a href='./'>Voltar página</a>";
                                out.println(erro);
                                return;
                            } else erro = "Ocorreu um erro ao deletar o funcionario!";
                        } else erro = "O ID precisa ser acima de 0!";
                        } catch (Exception e) { out.println(e.toString()); }
                    }
                    break;
                }
            }
            
            if(erro.length() > 0) out.println(erro+"<br><br><a href='javascript:window.history.back();'>Voltar página</a>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
