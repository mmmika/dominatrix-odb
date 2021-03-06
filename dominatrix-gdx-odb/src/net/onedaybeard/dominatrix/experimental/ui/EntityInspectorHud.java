package net.onedaybeard.dominatrix.experimental.ui;

import net.onedaybeard.dominatrix.artemis.JsonEntitySerializer;
import net.onedaybeard.dominatrix.experimental.artemis.CommandUtils;
import net.onedaybeard.dominatrix.experimental.artemis.CommandUtils.ObjectNode;
import net.onedaybeard.dominatrix.util.Tree;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

/**
 * Shows component breakdown of an entity. Has two view modes, textual and json.
 */
public final class EntityInspectorHud
{
	private Skin skin;
	
	private Table table;
	private Label hovered;
	private boolean visible;
	
	private Entity lastEntity;
	
	private enum View {ENTITY, JSON};
	private View view;
	
	private JsonKeyResolver jsonKeyResolver;
	
	private JsonEntitySerializer marshaller;
	
	public EntityInspectorHud(Skin skin, Stage ui, World world)
	{
		this.skin = skin;
		
		initialize(world);
		ui.addActor(table);
		
		setVisible(false);
		view = View.ENTITY;
		
		marshaller = new JsonEntitySerializer(OutputType.json);
	}
	
	public void setEntity(Entity e)
	{
		if (lastEntity == e)
			return;
		
		lastEntity = e;
		
		if (lastEntity != null)
			
			hovered.setText(getTextForEntity(e));
		else
			hovered.setText("");
		
		table.setVisible(visible && e != null);
		table.pack();
	}

	protected void initialize(final World world)
	{
		table = new Table(skin);
		
		hovered = new Label("", skin);
		
		table.setBackground(BackgroundTexture.getDrawable());
		table.defaults().pad(5);
		table.row();
		table.add(hovered);
		table.pack();
	}
	
	private String getTextForEntity(Entity e)
	{
		String text = null;
		switch (view)
		{
			case ENTITY:
				text = CommandUtils.formatTree(
					CommandUtils.feedComponents(new Tree<ObjectNode>(), e), null).toString();
				break;
			case JSON:
				text = getJsonForHovered().replace("\t", "    ");
				break;
			default:
				assert false : "Missing view " + view;
			break;
		}
		
		return text;
	}
	
	public String getJsonForHovered()
	{
		if (lastEntity == null)
			return null;
		
		String key = jsonKeyResolver != null ? jsonKeyResolver.getKey(lastEntity) : null;
		String text = marshaller.toJson(lastEntity, key).toString();
		return text;
	}
	
	public void toggle()
	{
		table.setVisible(!table.isVisible());
	}
	
	public boolean isVisible()
	{
		return table.isVisible();
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
		table.setVisible(visible);
	}
	
	public void cycleInspectorView()
	{
		View[] views = View.values();
		view = views[(view.ordinal() + 1) % views.length];
		
		if (lastEntity != null)
			hovered.setText(getTextForEntity(lastEntity));
		else
			hovered.setText("");
		
		table.pack();
	}
	
	public void setOutputType(OutputType outputType)
	{
		marshaller.setOutputType(outputType);
	}

	public static interface JsonKeyResolver
	{
		String getKey(Entity e);
	}
}
