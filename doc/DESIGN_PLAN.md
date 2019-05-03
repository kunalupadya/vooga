# DESIGN_PLAN spec

Structure the plan document in the following sections:

## Introduction
*This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). Describe your chosen game genre and what qualities make it unique that your design will need to support. Discuss the design at a high-level (i.e., without referencing specific classes, data structures, or code).*

In this project, our team will be creating a suite comprising game authoring environment and game player that will allow users to create and play their own tower defense games. The problem we are solving is that games we play today are too constrained.

Our goals are to make the games that we create different enough that they can provide different challenges to users but similar enough that they are tangibly and by feel, members of the same game genre.

We are choosing tower defense games that provide interesting challenges because parts of the game are manipulated (towers are moved) by players DURING gameplay, whereas the architecture of the game is mostly static in other game types. Another challenge of tower defense games is that users have the ability in the authoring enviromnet to define new types of weapons/enemies and their parameters.

## Overview
*This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. Describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's API. Include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). Discuss specific classes, methods, and data structures, but not individual lines of code.*

The modules we currently have are Authoring, Configurations, and GamePlayer.

The Authoring Environment's goal is to create and serialize a Game object. The way that it does this is by presenting the user with a screen from which they can load or start a new Authored Game which they will write. We utilized reflection on Configurable objects (more about that in the next section)to prompt the user for the correct information about each component of the game that is being created so that we get all of the information that we need about each component of the game. The Authoring environment also allows users the opportunity to save an Authored game and to ask for Help. The end product of this module is a JAR that holds the info needed to run the game, including the XML of the Game object.

Our next module is the Configurations module. This can be divided into two modules- Configs and ActiveConfigs. The ActiveConfigs build on the Configs, so we'll start on the Configs. Configurations define the hierarchy for our Game object. For example, each Game has Levels, which have a Map that dictates the terrain for the level, Arsenals (which have Weapons (which have Shooters (which create Projectiles))), Waves (which create Enemies). Another key thing that we implemented custom annotations on each component of each  Configuration to specify whether that variable is something that should be defined by the user for a specific object. 

A key design feature that we think provides us nearly unlimited extensibility is that all of our configurations that need to end up displayed on the player essentially start with the same components in their configuration- a label(name), a Image, and a list of behaviors. The behaviors part is key since it provides a uniform interface to interact with and add functionality to any component. We plan to implement many more behaviors during the second sprint.

The second portion of our Configurations authoringBackend is the ActiveConfigs package. These are the components that need to be alive and updated during the playing of the game. We decided to separate these from the 'template' style of configurations found in the Configuration folder due to the fact that a good amount of our functionality for actually playing the game is impossible to configure in the authoring environment and makes it more difficult to serialize. All the ActiveObjects are only loaded in and utilized in the ActiveLevel, which is an instance variable of a Game that is being played (only given a value in player). Many of these objects implement Updatable, and Updatable is what ends up determining how the component will act while the GameLoop is run from the frontend. Based on the hierarchies enumerated above, when Game.update() is called, the update method will cascade down to the ActiveLevel.update(), which will continue the cascade down to the individual components update() methods, like Weapons, Enemies, etc



In the Player environment, when a specific Game object is loaded, the Game Engine/GamePlayer will create a GameInstance that is then run in the Engine. The Player Visualization will then call Update() on this object and the Engine will apply the rules of the game to the object to update the map.

Here is a picture that represents our overall architecture as sketched out

<INSERT WHITE BOARD PICTURE HERE>

Here are UMLs that further elaborate our design and structure

<INSERT UMLS HERE>

## User Interface
This section describes what components the user will interact with in your program (keep it simple to start) and how a game is represented to the designer and what support is provided to make it easy to create a game. Include design goals for the implementation of the UI that shows the kind of abstractions you plan to build on top of OpenJFX's standard components and how they can be flexibily exchanged within the GUI itself. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).


The User will end up interacting with either of our modules through their Views, as part of an MVC authoringBackend. 

Here is a mockup of what we envision the authoring environment will look like. 

<Insert wireframe for authoring environment>

The authoring environment will make it easy to design a game because the optionality will be intentionally limiting. The user will have the option to create multiple levels in a game, and then on each level, they will be able to drag and drop elements that end up creating the background(map) of the game and also determine the types of weapons enabled in each level, and when things might be unlocked. 

On the Play side, users will select a game from the library of all created games to play, at which point that game's splash screen will appear. Once they click through that screen, they'll be taken to the 'Build' phase of the first level (which will be loaded in as the game's current ActiveLevel). Each level will have a Build phase during which the user will place weapons on the map to prepare for the incoming waves of enemies. They'll drag and drop weapons from the arsenal panel onto the map, at which point those weapons will be added to the ActiveLevel. Once they are done building, they will click the PLAY button for the current level, which will start the 'play loop' for the game. At each frame, the frontend will call game.Update which will cascade down into each component as described above.


## Design Details 
*This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). Describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Look for opportunities to share APIs between your sub-teams (e.g., authoring and player front ends or authoring and engine back ends) that may themselves be separate modules (like Java and OpenJFX are composed of several modules). Note, each sub-team should have its own external and internal APIs. Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions.*

We'll take a look at this from a module-by-module perspective.

Starting with the authoring environment, every launch of the authoring environement will start with a blank game into which things can be loaded or added modularly. The core of a game is different levels. There is a hierarchy of interfaces shown in the UML above that indicate what and how some sorts of objects can be created. Players will indicate when different weapons are unlocked, their price, and what the maps of each level will look like by dragging and dropping terrain tiles. They will also be able to create new types of weapons with new imagery or imagery, and with various Behaviors (whose parameters will be defined) from a library. They'll also be able to determine how and when enemies and other characters will spawn and what their behavior will look like (see the interfaces above), by creating Config objects for each of these types. The way that these Config objects are created with the correct parameters is due to the fact that we created custom annotations that the frontend of the authoring environment reflects over in order to prompt the user to give the correct information.

Once this game object is created and needs to be packaged into XML, the "save" button in the authoring environment will spur action to occur in the Data Handler. Using XStream as discussed in class, the Game object will be serialized into an XML file that holds all the necessary information to create an instance of the game that can be played by the Player.

Then, once the player is opened and that game corresponding to a certain XML file is selected, the Player/GamePlayer will unpack that XML file into an instance of the game, load the first level that game into the ActiveLevel instance variable of the game, and allow the game to start. Users will see the splash screen, be given information about their game (on the player mockup) and be able to spend cash to place weapons to defeat enemies, then press play and play the current level. Killing enemies will earn points and money and between levels, players may have the opporutnity to buy updated equipment and weaponry to enable certain things in their myArsenal. To display the game screen on the visualization, the player will need to reference resource objects to load the appropriate images into the visualization. Dragging and dropping on the visualization will need to be handled by queries to the game logic, so constant communication between these two parts of the player module are necessary in order to tell players when theyre performing illegal actions (which might be indicated by a red or green indicator), when they don't have enough money to do something, and to notify them of what else might be coming up in the game. Additionally, the player will have a heads up display that will show important information to the user. The visualization will query the engine to make these determinations, as this module pretty strictly follows the open/closed prinicple in terms of separating responsibility for rendering and calculating the correct things to display between the vis/engine.

The reason that we decided to separate our project into the authoring module, the configuration module, and the player module is that there are pretty clear lines that separate responsibilities in terms of what the goals of our project are with respect to these modules. Authoring is not closely tied to the logic (rather, the objects that are created in the authoring environment and processed through data will be executed and logic run on them upon the creation of a game instance in the Player module), so we decided to abstract everything that helps create a Game object (that will later get serialized) into the authoring module. 

The datahandler is pretty simple in that it is able to package and unpackage a Game into an XML file and an XML into a GameInstance object that can be used by the Player. It will also handle some of thee responsibiliteis of saving games in progress. 

## Example games
*Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help clarify the abstractions in your design.*

1) Bloons Tower Defense
A level based game where enemies take a givne path at periodic intervals and new weapons are unlocked, and users can rebuild/rearrange weaponry between weapons and unlock certain things given the amount of money/score they've accumulated

2) Defense competition (Waves)
Something more like COD: Zombies, where given a specific map and finite resources, players can create a setup and see how many points/how double the setup can last versus a specific configuration of waveConfigs that continues until the defense is broken/a certain amount off enemies are in

3) Creative Mode (FreeStyle)
Users can create a setup for weaponry (NOT THE MAP! Map will be created in authoring environment) and  try to maximize the number of points they can get given a certain set of resources.


## Design Considerations 
*This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.*

Some of the bigger design decisions we have ahead of us include deciding how data will be transmitted across parts of the code, how the grid of the map will work in terms of placing and moving objects, and figuring out where responsibilty will be allocated throughout the authoring process and how we will keep track of the game object being constructed. 