<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de Java, Android, Ruby e muito mais - Casa do Código</title>
</head>
<body>
	
	<h1>Lista de Produtos</h1>
	
	<div>${sucesso}</div>
	
	<table>
		<tr>
			<td>ID</td>
			<td>Título</td>
			<td>Descrição</td>
			<td>Páginas</td>
			<td>Data de Lançamento</td>
		</tr>
		
		<c:forEach items="${produtos}" var="produto">
			<tr>
				<td>${produto.id}</td>
				<td>
					<a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.titulo}</a>
				</td>
				<td>${produto.descricao}</td>
				<td>${produto.paginas}</td>
				<td>${produto.dataLancamento}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>