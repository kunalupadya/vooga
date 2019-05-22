# Use Cases

### Game player visualization

Mark's Use Cases(12):

Load Button: In the initial splash screen, we will have a load button where we can upload an XML file. The XML file specifies the game that was created in the authoring environment. The XML file is parsed through the backend, and the frontend recieves an instance of a game object. We use elements of the game object to display the game. 

Play Button: When we press play after loading a game, we will use the Game object to load the images needed to create the map and the myArsenal. This will be done in a grid fashion so we will load an image for each portion of the grid. 

Dragging a Tower: We will use the draggable in JavaFX to drag an imageview into the map. We will add a changeListener in order to check each time the position changes and then change the radius color to red or green depending on whether that position is valid for the tower. 

<<<<<<< HEAD
Hover over an Arsenal: We will use a hover over feature of javafx. When the mouse hovers over a weapon inside of an arsenal, we can see how many coins it will cost and the beahvior of it in a text format. The format will be called from the object that we created (Game object). 
=======
Hover over an Arsenal: We will use a hover over feature of javafx. When the mouse hovers over a weaponConfig inside of an myArsenal, we can see how many coins it will cost and the beahvior of it in a text format. The format will be called from the object that we created (Game object). 
>>>>>>> e359d39c535474f2a421b7b7920361e6858a7860

Starting an Animation: Each time step, we will call .update() on the backend Game object and we will be able to access new positions and images to display in the map. 

Docking down a life: When an enemyConfig travels on the path outlined in the map, we will check if it reaches the end of the path. If an enemyConfig reaches the end of the path, we will dock down a life using the backend game object. There would be a method that says dockLife(). 

<<<<<<< HEAD
Killing an Enemy: During the animation and each time step, each weapon that the towers throw out will be checked if it intersects with the enemy imageview. Then, the enemy imageview will disappeaer/ animate to blow up. 
=======
Killing an Enemy: During the animation and each time step, each weaponConfig that the towers throw out will be checked if it intersects with the enemyConfig imageview. Then, the enemyConfig imageview will disappeaer/ animate to blow up. 
>>>>>>> e359d39c535474f2a421b7b7920361e6858a7860

replay the game repeatedly without quitting: We will have some sort of restart button or replay button that will both be available in the overhead display as well as in a splash screen that pops up when you die. We will also have a button that will take us back to the level screen so a user can select a specific level to replay. 

switch games repeatedly without quitting: We will have some sort of button inside of the game that will state "load new game." When we press this button, it will take us back to the initial splash screen with the button 'load' and it will stop all animations and restart the backend engine to read a new xml file and start a new game. 

see which games are available, including at least each game's name, image, and description: Each time we load a new game from the initial splash screen, we will store that XML file within our application. We will then get its game name, image, and description displayed in a side bar inside of that initial 

save their progress in the game to restart later (perhaps only at specific save points): We will have a save button that saves the progress of the game. However, we will only save a couple key points of this progress: score, coins, lives, and level: These key data points will be saved in the front end as part of the 'recently played' game list and when the user loads a recently played list from the load up, we will pair that data with the saved game to pull up a specific instance of the game. 

set preferences specific to each game played: If a user wants a "hard" preference for a specific level, we will have to set the speed for how fast the enemies are being passed through the pathway. We will call a some sort of "setSpeed()" on the game engine so that the we will change the way update() is being called. 


### Game Engine

Brian's Use Cases (7):

Adding a level:
    While using the game authoring environment to create a new game, the user will likely want to create multiple levels for a game.  This will require a displayed list of levels created in the authoring environment adouble with a button to create a new level.  When creating a new level, there will need to be a level display window that outlines the map of the level and gives the user the ability to add objects to the map.  In the backend of the game authoring environment, the new level will need to be added to a list data strucutre holding information about each level.
    
<<<<<<< HEAD
Formatting a projectile:
    While designing a level in the game authoring environment, the user will be designing weapons that will be available in the arsenal of the user.  Part of the weapon design will be designing the projectile that is produced by the weapon.  A section of the authoring environment will be needed for input while designing a specific object.  In this case the user should be able to select from an image file as well as select from a list of effects the projectile can have upon collision.  In the backend, these design decisions of the user will be saved as string attributes of the projectile config object being created.
=======
Formatting a projectileConfig:
    While designing a level in the game authoring environment, the user will be designing weapons that will be available in the myArsenal of the user.  Part of the weaponConfig design will be designing the projectileConfig that is produced by the weaponConfig.  A section of the authoring environment will be needed for input while designing a specific object.  In this case the user should be able to select from an image file as well as select from a list of effects the projectileConfig can have upon collision.  In the backend, these design decisions of the user will be saved as string attributes of the projectileConfig config object being created.
>>>>>>> e359d39c535474f2a421b7b7920361e6858a7860
    
Creating Vehicle Path:
    While creating a level in the authoring environment, the user will have the possibility to add a vehicle to the game player's myArsenal.  One attribute of the vehicle that will have to be set is the path in which it takes in the game map.  One way the designer could set this attribute would be clicking and dragging the cursor within the level map window with the endpoints of the path being set entry and exit points to choose from.  This could be visualized by the front-end as a line being drawn and processed by the backend as an array of location values set in the vehicle config object that describes the path the vehicle will follow in the given level.

Set Destroy Animation of Tower:
    When a user is going through the process of creating a tower, each of the design aspects of the object that the user needs to decide upon must be displayed in a box within the authoring environment.  In the case of a tower, one of these attributes will be the destruction animation of the tower.  When the user clicks to set the attribute, a list of options should appear that can be selected from.  In the backend, this would come in the form of a set list of animation files that the user has access to choosing and the name of this file being set as a string attribute within the new tower config object being created.
    
Set Number of Enemies in a Wave:
    Part of a level in tower defense will be waveConfigs of enemies that will move from one part of the screen to another.  A level will have a list of waveConfigs and an attribute of a wave will be the number of enemies in the wave.  This could be set by the user as an input text box in the frontend that the user will type a number into.  This would then translate to the setting of an attribute within the wave config object to the number of enemies value passed from the frontend.
    
Changing the Image Size of a Game Element:
    When adding elements to a level in the authoring environment, a user might want to change the size of the element being added to the map.  One way a user should be able to do this is through a text input of a height and width value.  This will be stored in the backend as atribute values stored in the config object of the element.  Another way this could be edited by the game designer is by using click and drag size adjustment similar to image modification in powerpoint for example.  When the user ends the drag, the new height and width attribute will automatically be passed to save in the config object in the same way.
    
Loading a Game in the GamePlay Environment:
    When a game is selected to run in the GamePlay Environment, the game logic portion of the code will receive a game configuration xml file which fulfills the role resembling that of a game disk.  The other piece of information this portion of the code needs is a game instance state which is either specific to a user or a default state.  The game logic will read the game configuration file and process the information to create a game instance object that contains the backend details of what goes on during a game.  This game instance object is updated with the game instance state so a user can pick up playing where they left off.
    






Different 

Christina's use cases (6):
* **Creating a new type of game:** To create a new type of game, a new `GameRules` subclass should be created. This will allow the type of game to be switched out to another type of `GameRules`.
* **Restarting the game instance during game play (replay):** Within the `GamePlay` class, there will be a game loop that is controlling each game that is currently being played. If a player wants to restart a game, they can click a button, which will call `replay()` on the backend API that will end the loop. Then, it will reset the level back to 1, reset coins, reset time, and remove all weapons that were placed on the map. Then, the loop will be called upon to start running again.
* **Add a rule to the game:** Within the game logic module, there will be separate classes that define the specific games that can be played. These will all take part of a `GameRules` hierarchy and will be dependent on objects that can be used within any game (`Weapon`, `Block`, etc). All of the rules for each game will be defined their respective classes. There will be a `evaluate()` method within each one of these classes that is called through an outside loop. Within this method, rules for the game will be defined and logic can be added to define a new rule.
* **Unlock a new weapon:** During a game, the user will be able to unlock weapons as they earn coins or as time goes on. All of these statistics will be kept track of within a `GamePlay` class. Therefore, whenever a certain point is reached, the `Arsenal` that is being stored within `GamePlay` will be called upon to unlock a specific weapon. This will then be updated on the frontend through an API call that triggers it to update the `ArsenalView`.
* **User advances to next level/wins:** Within the `GamePlay` class, if no enemies are left within a certain level, the user should either advance to the next level or win. Therefore, the game loop should be checking each run whether there are enemies left. If it's already the last level, then the frontend should be notified that the game is over, which will trigger them to display a end screen with the score. If it isn't the last level, then the `GamePlay` class should get the next `Level` object within the `Game` object. Then, the `GameRules` object should then be passed in this new `Level` object and the game loop loop can be run again.


### Authoring Environment

* **Adding an enemyConfig that enters the map at a certain rate:** In the GUI, the user will be able to define when enemies enter the screen. If they define a certain enemyConfig to enter the screen every 2 seconds, they will be able to type in that value. When this is sent to the backend, a new `EnemyConfig` (implements `EnemyConfigInterface`) object will be created. Then a `setEnteringRate(int ms)`, adouble with other setters, will be called so that the object will contain an instance variable of the value `5`.

* **Add new tower types:** New tower types will be created by creating a new TowerConfig object, and setting the image, towertype, weapon type, and name of the object. This new tower may only be available on certain terrain types. This towerconfig will then be packaged with the game xml object and used by the game player to display all of the tower options available to the user. 

* **Add new terrain type:** New terrain types will be created by creating a new TerrainConfig object, and setting the image and name of the block. The terrain can be a walkable path, or it may be a different type of of terrain (water, sand, dirt, rock, wall, or other). This terrainconfig then gets added to the list of available terrains, and can be used to define the map.

* **Define the structure of the map:** The map will be a grid, that has a scaled number of tiles depending on the screen size. For each tile on the map, you should be able to set the terrain type at that point in the authoring environment. This should be done by using the terrainconfig objects that are available in the authoring environment. Each terrainconfig should be referenced in each point on the map that it is present.

<<<<<<< HEAD
* **Add a new weapon type:** You should be able to add a new weapon type in the frontend of the authoring environment, which will change the speed of shooting a projectile and the direction of shooting the projectile (homing, radial, line). This weapon type is then selectable as an option for each tower within the authoring environment. Some initial options can be defined within a properties file.
=======
* **Add a new weaponConfig type:** You should be able to add a new weaponConfig type in the frontend of the authoring environment, which will change the speed of shooting a projectileConfig and the direction of shooting the projectileConfig (homing, radial, line). This weaponConfig type is then selectable as an option for each tower within the authoring environment. Some initial options can be defined within a properties file.
>>>>>>> e359d39c535474f2a421b7b7920361e6858a7860

* **Add a new projectileConfig type:** You should be able to add a new projectileConfig type in the frontend of the authoring environment, which will create a new projectileConfigPackage.ProjectileConfig object that defines the image, projectileConfig type (bomb or bullet), and kill power.

* **Add number of lives:** User can put in number of lives the player will begin with. Involves simply setting an integer for lives that will be given to the back end to determine number of lives instance variable for game object.

* **Add save config file option:** User can click "save as xml" button that will save current game configuration into an xml file that the user can download. Implements an xml file builder class

* **Add button for user to load their own image file:** User can click button in environment that opens file chooser for user to upload image file for certain game objects such as towers, projectiles, etc.

* **Add ability for user to decorate load screen:** User can move around buttons, enter positions for x and y for buttons and also load background image for their game instance.

* **Add ability for user to enter number of levels:** User will type in integer value for number of levels the game will have, and this value passed back to be added as instance variable to game object.
### Data Mechanisms