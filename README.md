# CS102: Programming Fundamentals II : Parade Card Game


## Requirements
```
- JDK 21
- Able to read english to follow the instructions below
```


## Project Setup
Please follow the instructions before running the files


## How to Run
On the file directory of the project, do the following:
```
## MAC OS ##
sh compile.bat
sh run.bat


# OR if you prefer bash...
bash ./compile.bat
bash ./run.bat


## WINDOWS ##
./compile.bat
./run.bat


```
# Game Overview


## Goal of the game
Players play their hand of cards while trying not to get any cards from the parade. Nobody wants points in Wonderland.


## How to play
+ Choose human vs computer(s) or human vs human(s)
+ Choose number of human player(s) (if applicable)
+ Choose number of computer(s) (if applicable)
+ Choose Computer Difficulty (if applicable)
+ Enter player(s)’ name
+ Each round, select your cards to play by inputting card index from 0 to 4
+ Repeat until game over... 🎉
+ Each Player/Computer chooses 2 cards to discard
+ Winner is announced! 🏅


## Game Components
66 cards in 6 colours (with values ranging from 0 to 10) 


## Preparation of the parade
The start player is determined randomly. The deck is shuffled and each player receives 5 in their hand. 6 more cards are placed face up, which are the initial participants of the parade. The remaining cards are used as the draw pile.


## Player turn
During a player’s turn, the player performs the following actions in the order given below.
1. Choose one of the cards in hand and place it at the end of the parade (right side of the parade).
2. If necessary, cards will be removed from the parade and added to the players’ collected cards.
3. Draw a card from the draw pile. The next player performs the same actions during his or her turn.


## Endgame
When a player has collected all 6 different colours or the deck runs out of cards, the game continues for one more round. After the end of the game, each player chooses 2 cards from their hand and discards them. The remaining cards in hand are added to those already collected by the player.


The cards still in the parade are discarded and the collected cards of each player are used for scoring.


## Scoring
1. The player or players with the most cards in each color flip those cards over and each of these cards counts as 1 point. (The value printed on the cards is not counted.)
2. Each player will then add up the printed values of all their face up cards. Sum up this total with the total obtained from any face down cards. 


## Winner
After scoring, the player with the least amount of points wins. In the case that two players have the same number of points, the player with less cards collected wins. In the event that players have both the least amount of points and the same number of cards, the game ends in a draw.

