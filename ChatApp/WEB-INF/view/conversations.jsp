<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>

<!DOCTYPE html>
<html>
<head>
	<title>Conversations</title>
	<link rel="stylesheet" href="/ChatApp/css/main.css">	
</head>
<body>

<div class="mdl-layout__container">
	<nav>
		<a id="navTitle" href="/ChatApp">CodeU Chat App</a>
		<a href="/ChatApp/conversations">Conversations</a>
		<% if(request.getSession().getAttribute("user") != null){ %>
			<a>Hello <%= request.getSession().getAttribute("user") %>!</a>
		<% } else{ %>
			<a href="/ChatApp/login">Login</a>
		<% } %>
	</nav>


	<div id="container">	

		<% if(request.getAttribute("error") != null){ %>
				<h2 style="color:red"><%= request.getAttribute("error") %></h2>
		<% } %>

		<% if(request.getSession().getAttribute("user") != null){ %>
			<h1>New Conversation</h1>
			<form action="/ChatApp/conversations" method="POST">	  	
			  	<div class="form-group">
			  		<label class="form-control-label">Title:</label>
					<input type="text" name="conversationTitle" class="form-control">
				</div>

				<button type="submit">Create</button>
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
			<ul class="mdl-list">
		<%
			for(Conversation conversation : conversations){ 
		%>
			<li><a href="/ChatApp/chat/<%= conversation.getTitle() %>"><%= conversation.getTitle() %></a></li>
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
