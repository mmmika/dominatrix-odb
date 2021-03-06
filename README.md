## Take Note

*This small library was initially conceived during the early ECS days and hasn't really been keeping 
up-to-date with developments in [artemis-odb](https://github.com/junkdog/artemis-odb). While some of the
functionality is useful, it's a little too diverse. The good bits could favorably be migrated to [artemis-odb-contrib](https://github.com/DaanVanYperen/artemis-odb-contrib)*

dominatrix-odb is a small, but diverse library for working with [libGDX](http://libgdx.badlogicgames.com/) + 
[Artemis Entity System Framework](http://gamadu.com/artemis/), primarily dealing with runtime introspection and live editing.

## Features
 - EntityFactoryManager for creating entities from json.
 - Serialize entities to json.
 - Type-safe annotation-driven injection from _.properties_ files.
 - UI views for:
    - reflexively editing entities 
      ([screenshot 1](https://raw.github.com/wiki/junkdog/dominatrix-odb/images/rebelescape-editor-reflexive-03.png))
    - toggling entity systems
      ([screenshot 2](https://raw.github.com/wiki/junkdog/dominatrix-odb/images/rebelescape-004-systems.jpg))
    - adding components to entities
      ([screenshot 1](https://raw.github.com/wiki/junkdog/dominatrix-odb/images/rebelescape-editor-reflexive-03.png))
    - inspecting entities (pretty-printed/json)
      ([screenshot 3](https://github.com/junkdog/dominatrix-odb/wiki/entity-inspection-psytripper))

## Assumptions/Limitations/State of Affairs
 - Components are required to reside in a single package: imposed by component  discovery and
   json unserializing.
 - Uses a slightly modified [fork](https://github.com/junkdog/artemis-odb) of artemis, allowing
   for systems to be toggled at runtime.
 - The demo uses [Project Lombok](http://projectlombok.org/) to reduce boilerplate nonsense. See their site
   for Eclipse/Netbeans installation. (At a later stage, there may be a delomboked - vanilla java - version).


## Usage
Javadocs are a little scarce at the moment, see demo project for details.

### The EntityFactoryManager
In the screen or main class:
```java
    entityFactoryManager = EntityFactoryManager.from(
        Gdx.files.internal("data/archetypes.json"), JsonKey.class, true);
                
    World world = new World();
	world.setManager(entityFactoryManager);       
    // more artemis managers/systems
    
    world.initialize();
```

In the render method, make sure to call `entityFactoryManager.addNewToWorld()`; this
has to be done outside of Artemis' processing loop.

To create an entity, simple call `entityFactoryManager.create("name-of-entity")`.

### Demo
Import _dominatrix-demo_ into eclipse as a Maven project, alternatively run `mvn package' inside the
demo folder,  this creates an executable jar-with-dependencies under _dominatrix-demo/target_.
After starting, press **F1** to toggle keyboard shortcuts: the shortcuts demonstrate the bulk of
the functionality.

### Maven

**Nota Bene:** Maven repository on github isn't up to date; dominatrix has to be manually
installed (`mvn -Pgdx-odb install`) prior to being used.

```xml
    <repositories>
    	<repository>
    		<id>dominatrix-odb</id>
    		<url>https://raw.github.com/junkdog/dominatrix-odb/mvn-repo/</url>
    		<snapshots>
    			<enabled>true</enabled>
    		</snapshots>
    	</repository>
    </repositories>
    
    <dependencies>
		<dependency>
			<groupId>net.onedaybeard</groupId>
			<artifactId>artemis-odb</artifactId>
			<version>0.3.2</version>
		</dependency>
		<dependency>
			<groupId>net.onedaybeard</groupId>
			<artifactId>dominatrix-gdx-odb</artifactId>
			<version>0.2.0-SNAPSHOT</version>
		</dependency>
	</dependencies>
```
