<!DOCTYPE html>
<html>
<head>
  <title>Load Test Data</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
  </nav>

  <div id="container">
    <h1>Load Test Data</h1>
    <p>This will load a number of users, conversations, and messages for testing purposes.</p>
    <form action="/testdata" method="POST">
      <button type="submit" value="Y" name="confirm">Confirm</button>
      <button type="submit" value="N" name="donothing">Do Nothing</button>
    </form>
  </div>
</body>
</html>
