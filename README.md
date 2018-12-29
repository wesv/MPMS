# MPMS (MultiPlayer MineSweeper)

Multiplayer Minesweeper is a self-made project where you would have multiple people playing the same game of minesweeper while taking turns (that's the goal, anyway).

The different folders represent the different modules in the project. There's the <code>logic</code> folder which contains the logic used for playing the game. There's the <code>ui</code> folder containing all the ui code in an MVC pattern. Lastly you have the <code>ai</code> folder which contains the code used for the artificial intelligence.

The field is randomly generated and uses polymorphism to store all Mines and Numbers as <code>Tiles</code>.

The user interface is custom made in swing while being designed using the MVC pattern. The grid is dynamic and supports any N✕N grid size. Here's what it looks like now:

![UI](/resources/readme_ui_pic.png)

The AI is also custom made and can win most games if it doesn't hit a mine in the first few clicks. It uses the same <code>Model</code> from the MVC pattern on the UI so it only sees what you would see as a user. Here's the logic behind it:
1. Find the probability that each unflipped tile is a mine using the flipped tiles. 
2. Select the move whose probability of being a mine is low OR flag a mine who's probability of being a mine is really high

Lastly, Here's a list of things I'm still planning on implementing:
* A turn based system for playing with the AI/Another Player
* Ability for the AI to realize when a bad flag is present (it never places bad flags unless it's guessing and guesses wrong)
* Concurrency-safe code in the <code>logic</code> folder 
* A help button for asking for the ai to make a suggestion
* An updated Console UI using the <code>View</code> interface
* Client/Server code to play with someone else

And here's what I have completed:
* A Fully fledged Minesweeeper game using a console UI
* The same game with a Swing UI
* An AI player


