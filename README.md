# Project: Tic-Tac-Toe

Tic-tac-toe (American English), noughts and crosses (Commonwealth English), or Xs and Os (Canadian or Irish English) is a paper-and-pencil game for two players who take turns marking the spaces in a three-by-three grid, one with Xs and the other with Os. A player wins when they mark all three spaces of a row, column, or diagonal of the grid, whereupon they traditionally draw a line through those three marks to indicate the win. It is a solved game, with a forced draw assuming best play from both players.
![Tic-tac-toe.png](materials%2Fpictures%2FTic-tac-toe.png)

## Task 1. Creating the Project Structure  

- Each layer is a separate package.  
- The project structure must include the following layers: web, domain,datasource, di.  
- The web layer must contain at least the packages model, controller, mapper forinteraction with the client.  
- The domain layer must include at least the packages model, service forimplementing the business logic of the application.  
- The datasource layer must include at least the packages model, repository,mapper for implementing data operations (for example, working with adatabase).  
- The di layer defines the dependency injection configurations.  

## Task 2. Implementing the Domain Layer  

- Describe the game board model as an integer matrix.  
- Describe the model of the current game, which has a UUID and a game board.  
- Describe the interface of a service that has the following methods:  
  - A method to get the next move of the current game using the Minimaxalgorithm.  
  - A method to validate the current game board (check that previous moveshaven't been changed).  
  - A method to check if the game has ended.  
- Models, interfaces, and implementations must be in separate files.  

## Task 3. Implementing the Datasource Layer  

- Implement a storage class to store current games.  
- Use thread-safe collections for data storage.  
- Describe the models of the game board and the current game.  
- Implement domain<->datasource mappers.  
- Implement a repository for working with the storage class; it must have thefollowing methods:  
  - A method to save the current game.  
  - A method to get the current game.  
- Create a class that implements the service interface and accepts the repository(for working with the storage class) as a parameter.  
- Models, interfaces, and implementations must be in separate files.  

## Task 4. Implementing the Web Layer  

- Describe the models of the game board and the current game.  
- Implement domain<->web mappers.  
- Implement a controller using Spring that has a POST /game/{UUID} method(where {UUID} is the UUID of the current game). This method receives thecurrent game with an updated board from the user and returns the currentgame with the updated board for the computer's turn.  
- If an invalid current game or updated board is sent, the method should returnan error with a description.  
- The application must support multiple games simultaneously.  
- Models, interfaces, and implementations must be in separate files.  

## Task 5. Implementing the DI Layer  

- Implement a Spring Configuration class that describes the dependency graph.  
- It must contain at least:  
  - The storage class as a singleton;  
  - A repository to work with the storage class.  
  - A service to work with the repository.  

![App.png](materials%2Fpictures%2FApp.png)