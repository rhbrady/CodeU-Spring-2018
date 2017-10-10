# CodeU Example Project

This is a very rough example of the kind of direction we could go by using a servlet-based app.

# Step 1: Download Jetty

Download Jetty from [here](http://www.eclipse.org/jetty/download.html). Extract the `.zip` file anywhere you want. Putting it on your desktop for now is fine.

Your Jetty directory should look something like this:

- `jetty/`
  - (bunch of files and folders we don't care about)
  - `start.jar`
  - `lib/`
    - `servlet-api-3.1.jar`
    - (more files and folders we don't care about)
  - `webapps/`
    - (Web apps folders go here! More info below.)

# Step 2: Compile the `.java` files

## Anatomy of a Web App

We need to compile the `.java` files in the `src` directory, put the generated `.class` files in the `ChatApp/WEB-INF/classes` folder, and then copy the `ChatApp` directory into the `webapps` folder of our Jetty directory. Specific directions are below, but the end result should look like this:

- `ChatApp/`
  - `index.jsp`
  - `WEB-INF/`
    - `web.xml`
    - `lib/`
      `jsoup-1.10.3.jar`
    - `view/`
      - `chat.jsp`
      - `conversations.jsp`
      - `login.jsp`
    - `classes/`
      - `codeu/`
        - `controller/`
          - `ChatServlet.class`
          - `ConversationServlet.class`
          - `LoginServlet.class`
        - `model/`
          - `data/`
            - `Conversation.class`
            - `Message.class`
            - `User.class`
          - `store/`
            - `ConversationStore.class`
            - `DefaultDataStore.class`
            - `MessageStore.class`
            - `UserStore.class`
            
There are a number of ways to generate this. Here are some common options.

## Compiling with the Console

Create a `classes` directory inside `ChatApp/WEB-INF`.

Then from the top-level `codeu_example_project` directory, execute the following command:

**Windows**

```
javac -cp "src;C:\PATH\TO\jetty-distribution\lib\servlet-api-3.1.jar;ChatApp\WEB-INF\lib\jsoup-1.10.3.jar" -d "ChatApp\WEB-INF\classes" src/codeu/controller/*.java
```

**Mac or Linux**

```
javac -cp "src:/PATH/TO/jetty-distribution/lib/servlet-api-3.1.jar:ChatApp/WEB-INF/lib/jsoup-1.10.3.jar" -d "ChatApp/WEB-INF/classes" src/codeu/controller/*.java
```

(Change `PATH/TO` to wherever you placed your Jetty directory.)

You should now see the `.class` files inside the `classes` directory. Proceed to step 3!

## Compiling with Eclipse

- Download Eclipse EE.
- Go to `File > New > Other...`, then select `Web > Dynamic Web Project`. Click `Next`.
- Uncheck `Use default location` and change the location to your `codeu_example_project` directory. Name your project and then click `Next`.
- Change the `Default output folder` to `ChatApp\WEB-INF\classes`. Click `Next`.
- Change the `Content directory` to `ChatApp`. Click `Finish`.
- Right-click the project, go to `Properties`, then `Java Build Path` on the left, then the `Libraries` tab.
- Click `Add External JARs...` and add `servlet-api-3.1.jar` from your Jetty directory. Click `OK`.

Now Eclipse will automatically compile your `.java` files and put them in the `classes` directory whenever you make a change. Proceed to step 3!

## Compiling with Intellij

- Download Intellij IDEA
- Click `Create New Project` on the welcome screen.
- Select `Java Enterprise` and click `Next`. Click `Next` again to skip the template step.
- Change the `Project location` to your top-level `codeu_example_project` directory.
- Click `Finish`.
- Right-click the project and click `Open Module Settings`.
- Click `Modules` on the left and select the `Path` tab.
- Select `Use module compile output path` and change the `Output path` to your `ChatApp/WEB-INF/classes` directory.
- Click `Libraries` on the left.
- Click the add button (looks like a green + sign). Click `Java`, then select the `servlet-api-3.1.jar` file from your Jetty directory. Click `OK`.
- Click the add button again. Click `Java`, then select the `jsoup-1.10.3.jar` file from the `ChatApp/WEB-INF/lib` directory. Click `OK`.
- Click `OK` to close the settings dialog.
- Click the `Build` menu and select `Build Project`.

Whenever you select `Build Project`, Intellij will compile your `.java` files and put them in the `classes` directory. Proceed to step 3!


# Step 3: Deploy the web app

Copy your `ChatApp` directory to the `webapps` folder inside your Jetty directory.

# Step 4: Run Jetty

From your top-level Jetty directory, run this command:

```
java -jar start.jar
```

(Don't forget to check the console for errors!)

# Step 5: Use the web app!

Visit `http://localhost:8080/ChatApp/` in your browser. You should see the homepage of the chat app!

# Step 6: Make a change!

- Bring down the existing server by pressing `ctrl+c` in the console running Jetty.
- Modify a `.java` or `.jsp` file. If you change a `.java` file, don't forget to recompile!
- Delete the old `ChatApp` folder from the Jetty `webapps` directory.
- Copy your working `ChatApp` folder into the Jetty `webapps` directory.
- Run Jetty again.
- Refresh your browser to see your changes!

