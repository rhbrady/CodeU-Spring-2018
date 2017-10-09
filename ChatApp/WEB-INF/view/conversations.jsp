<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>

<!DOCTYPE html>
<html>
<head>
	<title>Conversations</title>
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<div class="container">
	<nav class="navbar navbar-default">
		<ul class="nav navbar-nav">
			<li><a href="/">CodeU Chat App</a></li>
			<li><a href="/conversations">Conversations</a></li>
			<% if(request.getSession().getAttribute("user") != null){ %>
			<li><a>Hello <%= request.getSession().getAttribute("user") %>!</a></li>
			<% } else{ %>
			<li><a href="/login">Login</a></li>
			<% } %>
		</ul>
	</nav>
	
	<% if(request.getAttribute("error") != null){ %>
			<h2 style="color:red"><%= request.getAttribute("error") %></h2>
	<% } %>
	
	<% if(request.getSession().getAttribute("user") != null){ %>
		<h1>New Conversation</h1>
		<form action="/conversations" method="POST">	  	
		  	<div class="form-group">
		  		<label class="form-control-label">Title:</label>
				<input type="text" name="conversationTitle" class="form-control">
			</div>
			
			<button type="submit" class="btn btn-primary">Create</button>
		</form>
		
		<hr/>
	<% } %>
	
	<h1>Conversations</h1>

	<% 
	List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");
	if(conversations == null || conversations.isEmpty()){
	%>
		<p>Create a conversation to get started.</p>
	<%
	}
	else{
	%>
		<ul>
	<%
		for(Conversation conversation : conversations){ 
	%>
		<li><a href="/chat/<%= conversation.getTitle() %>"><%= conversation.getTitle() %></a></li>
	<%
		}
	%>
		</ul>
	<%
	}
	%>
	<hr/>
</div>
		
</body>
</html>