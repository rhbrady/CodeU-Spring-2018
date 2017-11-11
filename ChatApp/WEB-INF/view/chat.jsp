<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.BasicUserStore" %>
<% 
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
	<title><%= conversation.getTitle() %></title>
	<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<style>
		.chat{
			height:500px;
			overflow-y:scroll
		}
	</style>
		
	<script>
		// scroll the chat div to the bottom
		$(function(){
			var chatDiv = $('.chat')[0];
			var scrollHeight = chatDiv.scrollHeight;
			$('.chat').scrollTop(scrollHeight);
		});
	</script>
</head>
<body>

<div class="container">

		<nav class="navbar navbar-default">
			<ul class="nav navbar-nav">
				<li><a href="/ChatApp">CodeU Chat App</a></li>
				<li><a href="/ChatApp/conversations">Conversations</a></li>
				<% if(request.getSession().getAttribute("user") != null){ %>
				<li><a>Hello <%= request.getSession().getAttribute("user") %>!</a></li>
				<% } else{ %>
				<li><a href="/ChatApp/login">Login</a></li>
				<% } %>
			</ul>
		</nav>
		
		<h1><%= conversation.getTitle() %> <a href="" style="float: right"><span class="glyphicon glyphicon-refresh"></span></a></h1>

	<hr/>

	<div class="chat">
		<ul>
	<%
		for(Message message : messages){
			String author = BasicUserStore.getInstance().getUser(message.getAuthor()).getName();
	%>
		<li><strong><%= author %>:</strong> <%= message.getContent() %></li>
	<%
		}
	%>
		</ul>
	</div>

	<hr/>
	
	<% if(request.getSession().getAttribute("user") != null){ %>
	<form action="/ChatApp/chat/<%= conversation.getTitle() %>" method="POST">	  	
			<input type="text" name="message" class="form-control">
			<br/>
			<button type="submit" class="btn btn-primary">Send</button>
	</form>
	<% 
		} 
		else{
	%>
		<p><a href="/ChatApp/login">Login</a> to send a message.</p>
	<%
		}
	%>
	
	<hr/>
	
</div>
		
</body>
</html>