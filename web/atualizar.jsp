<%@page import="funcionario.FuncionarioDAO"%>
<%@page import="funcionario.Funcionario"%>
<%
int id = Integer.parseInt(request.getParameter("id"));
if(id > 0) {  

Funcionario func = new FuncionarioDAO().consultarID(id, null);
if(func.getId() > 0) {
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>ATUALIZAÇÃO DE SALÁRIO</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <fieldset>
            <form action="Controle" method="post">
                <b>Nome</b>: <%=func.getNome()%><br>
                <b>Salario</b>: <input type="number" name="salario" value="<%=func.getSalario()%>" required>
                <input type="hidden" name="id" value="<%=func.getId()%>">
                <input type="hidden" name="system" value="atualizar">
                <input type="submit" value="Atualizar">
            </form>
        </fieldset>
        <br>
        <a href="./">Voltar para o inicio</a> | <a href="javascript:window.history.back();">Voltar página</a>
    </body>
</html>
<% } else { %>
ID invalido!<br><br><a href='javascript:window.history.back();'>Voltar página</a>
<% } } else { %>
ID invalido!<br><br><a href='javascript:window.history.back();'>Voltar página</a>
<% } %>