# Presentation Plan

### Project Overview (Rishi) (2-3 min)

Outline of what type of game we're doing

Why we chose this game




### Design Overview

#### Highest level (Rishi) (2 min)

Three Modules

Authoring, Configuration, Player

Authoring environment goal is to get user input to define a full game package (Authored Game) an XML into a JAR that can be read by the player module to create a game.

Configuration: Defines what kinds of things can be made in the authoring environment, what parameters each of those things need, and also includes a package (ActiveConfigs) that define behavior of the objects that are 'live' during the actual running of a game in the player (the 'engine' of the game, the interactions, etc). The interactions are defined by once things are authored. these classes are used when the game is running

Player: This is where the game is played- as of right now, we can load in a jar and move through the game, moving through splash screens. 
splash screen, build phase, and run phase for each level.

### Design DiveIn

#### Configuration

##### Config Hierarchy (Sachal & Kunal) (not activeconfigs) (6-8 min)

configurable, behaviors, viewable, structure

##### Active Configs & Active Level (Rishi & Christina) (3-5 min)
update methods, lists held in active level

#### Authoring (4-6 min)

##### High Level (Louis)

How components are arranged, how the reflection works, etc

Process of configuring different things

##### Map (Hyunjae) (1-2 min)
User can select 


#### Authoring (4-6 min)

##### High Level (Louis)

How components are arranged, how the reflection works, etc

Process of configuring different things

##### Map (Hyunjae) (1-2 min)
User can select 



##### Authoring + Configs to Data (Brian) (3-4 min)

Game Objects are Created in Authoring View Using classes from the Configuration Module

Authoring View passes new Game Object to Authoring Model
    Loads and updates a properties file with Name, Thumbnail, Description, and XML file
    Serializes Game Object and passes saves it to a file
    
Game Play Model has GameLibrary Class
    getGameOptions() - Called by Game View
    Reads in Game Option Properties file and Converts the information to GameInfo Objects
    Game Play View uses this to display game options
    Game Play Model retrieves XML file of selected game and un-serializes the Game Object
    
Saving is completely automated to prevent user error or tampering

#### Interactions and APIs

XStream Setup

##### Interactions of Config and Player during gameplay (Brian)(public methods of Game/Game Logic) (3-4 min)

Model and View Design for each half of the game

Game Authoring
    Model's sole responsibility is managing saved Game data through saveToXML()
    
Game Play
    Model's role is similar to traditional game engine
    All state is maintained and updated within the Model
    The Game data is encapsulated from the View
        -example TransferImageView extends ImageView and implements ImmutableImageView
            getAsNode() method provides object for View to directly add to Display
            
    

Public methods of Game/Game Logic, API Calls



#### Player (Bryant & Mark) (4-5 min)

What it does once it has the Game object
Mark run through the load and stuff
Bryant will talk on containers and communication, getTerrain, getArsenal, game loop that calls, drag and drop functionality



#### Looking forward (4-5 min)

##### Upcoming Use Cases and Extending our Current Functionality (Rishi)

##### Utility Module (Sachal) (2-3 min)

##### Thoughts () 


### Demo () (5 min)

