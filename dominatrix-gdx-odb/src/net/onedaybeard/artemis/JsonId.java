package net.onedaybeard.artemis;

import com.artemis.Component;

/**
 * Json key component interface, as reported by the {@link JsonEntityMarshaller} and 
 * when referencing entity types for the {@link EntityFactoryManager}. 
 *
 * @see {@link EntityFactoryManager#from(com.badlogic.gdx.files.FileHandle, Class)}
 * @see {@link EntityFactoryManager#create(String)}
 */
public interface JsonId
{
	String name();
	Component name(String name);
}
