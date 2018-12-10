=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: jduhamel
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections
  All entities in the game are members of a linked list. This allows fast iteration through the
  collection of entities and removal without causing any problems with broken state. This also
  allows the entire game state to be updated by iterating through the collection and calling
  .update() on every entity. A implementation other than a collection would necessitate resizing and
  more difficult traversal of all entities.


  2. Advanced Topic: complex collisions
  Collision area are calculated by iterating through the sprite png image and creating an area that
  is representative of the boundary of that sprite's image. This creates very complicated and
  irregular collision boundaries. Collisions are calculated by checking if
  there is a non-zero intersection between the area's of two entities that are within 150 pixels of
  each other. If so the result of the damage function of the opposing entity is called on each
  entity. For images of what this looks like. The orange boundary is the collision boundary. They
  are rendered one frame behind the sprite.
  https://drive.google.com/open?id=1tkR-rbfhcEotTnRoef1cPJ6l95C-F7_E


  3. Inheritance/Subtyping
  Since there are many types of entities and sub entities in the game having everything extend a
  common class entity and extend lower classes with more specific function like Ship allows easier
  modeling of the game and greatly reduces the amount of code and redundancy. common methods such as
  update and move are used and occasionally overridden in  dynamic dispatch throughout the game.

  4. FileIO
  The game model state is saved by pressing the save button. This records current gametime, score
  and all the entities currently in the gamefield. pressing LoadGame on the main menu restores the
  current game time and score and reloads all the entities back into the game. These features use
  Buffered readers and writers to manage the gamesave file.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Oh no.....
  Game is a window mannager for the start of the game and calls the braching actions if needed to
  load a save or create a new game
  FileMannager handles the loading and saving of the game state. It adds/saves all entities and game
  state
  GameField mannages the game state and runs the game loop. It also loops through all entites
  calling update() and checking for collision. It also handles adding and removing entites and out
  of bounds entities. It also spawns asteroids randomly and handles the endgame.
  Enitity is an abstract class that all entities extend it also handles collision between entites.
  Ship in the superclass for PlayerShip and EnemyShip and handles drawing, update and damage
   supercalls for the ship types.
  Enemyship extends ship and contains the enemy AI attack and movement code.
  Playership extends ship and contains the player specific movement and update/attack code.
  Laser contains the code to draw the lasers, update them and ownership management for game
  score tracking.
  Sprite manages the spritesheet for each entity and allows them to play their animations.
  Statusbar draws the player's status bar and bar image files.
  BoundaryGen creates the entity collision area based on the entity png file.
  Asteroid draws and updates the large asteroids as well as handeling what happens when they
  get damaged and how to explode into smaller asteroids.
  AsteroidChunck draws and updates the smaller asteroids and handles their destruction and removal.
  EventScript reads the script.xml file and handles endgame/spawing of new enemies

  Boss and SpaceKraken are unfinished classes for the SpaceKracken and subsequent bosses.
  lowkey probably gonna switch the game to LWJGL and keep working on it over break




- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  I had one weird bug that related to casting the collision areas upwards and then calculating
  intersections based on that data. There weren't any significant problems related to overall
  implementation though.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  There is pretty good separation of functionality and private state encapsulation.
  Classes cannot directly affect the game state model without traversing through method calls to
  GameField and it's own checks and balances. The separation and encapsulation gets a little messy
  around the FileManager and EventScript classes but I think this is unavoidable due to their
  nature. If it had more time to refactor I would clean up the constructors for entities especially
  since I changed how object size was created. I would also optimize more by precalcuating the laser
  collision rotation transformation, this would significantly increase performance.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  googling why Swing was being annoying and not doing what I was telling it.
