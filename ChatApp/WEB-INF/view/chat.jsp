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
	<link rel="stylesheet" href="/ChatApp/css/main.css" type="text/css">
	
	<style>
		#chat{
			background-color: white;
			height: 500px;
			overflow-y: scroll
		}
	</style>
		
	<script>
		// scroll the chat div to the bottom
		function scrollChat(){
			var chatDiv = document.getElementById('chat');
			chatDiv.scrollTop = chatDiv.scrollHeight;
		};
	</script>
</head>
<body onload="scrollChat()">

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

		<h1><%= conversation.getTitle() %> <a href="" style="float: right">&#8635;</a></h1>

		<hr/>

		<div id="chat">
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
