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
$ ./compile.sh
$ ./run.sh

# OR if you prefer bash...
$ bash compile.sh
$ bash run.sh

## WINDOWS ##
$ compile.bat
$ run.bat

## LINUX ##
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
 + Select your cards to play by inputting 0 to 4
 ```
-------------------------------------------------------------------------------------------------------------------------------------------
Current Parade: [Purple 9, Red 6, Orange 0, Purple 7, Purple 5, Yellow 4]
Player 1 (Hand: [Purple 3, Orange 6, Green 4, Green 5, Yellow 9])
Computer 2 (Hand: [Yellow 8, Red 5, Yellow 0, Orange 5, Blue 4])
-------------------------------------------------------------------------------------------------------------------------------------------
Cards left in the deck: 50
Player 1, your turn!
Your Hand:
0: Purple 3
1: Orange 6
2: Green 4
3: Green 5
4: Yellow 9
Choose a card index to play: 0
Player 1 played: Purple 3
Player 1's Collected Cards: [Orange 0, Purple 9]
 ```
 + Repeat until game over... 🎉
 + Each Player/Computer chooses 2 cards to discard
```
Final round is completed. Game Over!
-------------------------------------------------------------------------------------------------------------------------------------------
Game Over!
Player 1's Collected Cards: [Yellow 8, Yellow 3, Purple 4, Yellow 4, Green 9, Green 7, Blue 0, Orange 9, Orange 4, Red 6, Red 9]
0: Yellow 9
1: Orange 0
2: Blue 7
3: Green 3
4: Green 2
Choose 1st card to discard: 0
-------------------------------------------------------------------------------------------------------------------------------------------
Updated hand after discarding the 1st card:
0: Orange 0
1: Blue 7
2: Green 3
3: Green 2
Choose 2nd card to discard: 1
-------------------------------------------------------------------------------------------------------------------------------------------
```
 + Winner is announced! 🏅


## Game Components
66 cards in 6 colors (with values ranging from 0 to 10) 


## Preparation of the parade
The start player is determined randomly. Starting with the starting player, the game continues clockwise. The deck is shuffled and each player receives 5 cards face down. 6 more cards are placed face up next to each other in the middle of the table. These cards are the initial participants of the parade. The game box is placed at one end of the parade to mark the front of the parade. The other side is called the end of the parade. The remaining cards are placed face down as the draw pile in the middle of the table


## Sequence of play
During his turn, a player performs the following actions in the order given below.
1. Choose one of the cards in your hand and lay it at the end of the parade.
2. If necessary, cards will be removed from the parade and laid in front of the player.
3. Draw a card from the draw pile. The next player performs the same actions during his turn


## Scoring
After the end of the game each player chooses 2 cards from their hand and discards them. The remaining 2 cards in hand are added to those already in front of the player. Note: Each of these 2 cards will either be added to colors you already own, or create a new pile (if you do not already have any cards of that color).


Only the cards lying in front of a player are scored. The cards still taking part in the parade are discarded.


For each color, players must determine the number of points they score. Each color is dealt with separately.
1. Determine who has the majority in each color. The player or players with the most cards in each color flip those cards over and each of these cards counts as 1 point. (The value printed on the cards is not counted.)
2. Each player will then add up the printed values of all their face up cards. Sum up this total with the total obtained from any face down cards. 
