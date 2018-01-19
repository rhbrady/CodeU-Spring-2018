<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
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
    <a href="/testdata">Load Test Data</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>CodeU Chat App</h1>
      <p>Welcome to CodeU! Rachel was here. This is an example chat application
        designed to make it easy to improve and build on. Here's some stuff to
        think about:</p>

      <ul>
        <li><strong>Algorithms and data structures:</strong> We tried to keep
          the code as simple as possible, but that comes at the cost of poor
          performance. Can you improve that?</li>
        <li><strong>Look and feel:</strong> The focus of CodeU is on the Java
          side of things, but if you're particularly interested you might use
          HTML, CSS, and JavaScript to make the chat app prettier.</li>
        <li><strong>Customization:</strong> Think about a group you care about.
          What needs do they have? How could you help? Think about technical
          requirements, privacy concerns, and accessibility and
          internationalization.</li>
      </ul>

      <p><a href="/login">Login</a> to get started, then go to the
        <a href="/conversations">conversations</a> page to create or
        join a conversation.</p>
    </div>
  </div>
</body>
</html>
