Dean Gaffney
20067423
Console Game Development - BunnyHop
Kieran Murphy

Project Rundown
---------------
* All steps covered in class are implemented.
* Winning a level step is completed.

Extended Game Rundown
---------------------
1. I introduced a timer into each level. The timer is of 2:30 secs and
rendered on screen of a format of mins:secs:millisecs.
When the timer runs out a message is displayed on screen informing the player they are out of time (WorldRenderer.java). The level is then restarted.

2. I introduced a level tracker for the player which informs them on screen
of what the current level is. When the player is on the final level it displays that it is the "Final Level".

3. I introduced the ability to gather several goals in each level and to have a minimum score level to advance to the next level. These are represented on the screen by an icon logo showing the remaining logos you have left, and a score counter showing how much score is needed to meet the minimum requirement.
The score counter is always a minimum of 2000 points (Constants.java), and when that mark is reached it no longer renders scores going past this mark(WorldRenderer.java).
If all the goals are collected but not enough coins are collectecd then the user is displayed a message showing them how many more coins are needed to finish the level.
The same holds true for the goals, if the minimum score requirement is met and not all the goals have been collected then the user is informed of this on screen through the WorldRenderer.

3. I introduced extra lives into the game which can be picked up only if the player has less than the max amount of lives (i.e 3), otherwise the player doesn't pick them up.
When they are picked up they replenish the players health by 1 and the icon is restored on the GUI.

4. I introduced extra levels into the game. The levels can be tested quickly by presseing the numbers 1-5 on the keypad. The numbers correspond to the level number. Each level has a different amount of goals that need to be collected and different layouts.
When the player has met the minimum score requirement for the level and has collected all goals in the level the player is informed that he has completed the level and then the player advances to the next level.
However if the player has done the above on the final level the player is informed they have won the game (i.e not the level).The game then starts again from level 1.

5. I introduced a new power up item in the game the "Java Coffee Cup".
When the player collects this the players speed is doubled and the player has automatic moving making it much harder to navigate over the rocks. It adds some speed and excitement to the game. 
The power up has a 9 seconds powerup time and is represented on the GUI similar to the feather.
The player also turns a transparent red colour when this powerup is active.

