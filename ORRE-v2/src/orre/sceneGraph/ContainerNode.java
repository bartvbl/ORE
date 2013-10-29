package orre.sceneGraph;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class ContainerNode implements SceneNode {
	protected boolean visible = true;
	protected ArrayList<SceneNode> children = new ArrayList<SceneNode>();
	protected float renderRadius = 0.0f;
	protected boolean parentHasBeenRegistered = false;
	
	public float getRenderRadius()
	{
		return this.renderRadius;
	}

	public void addChild(SceneNode node) 
	{
		this.children.add(node);
	}

	public void removeChild(SceneNode node) 
	{
		this.children.remove(node);
	}

	public void setVisibility(boolean isVisible) 
	{
		this.visible = isVisible;
	}
	
	protected void renderChildren()
	{
		for(SceneNode child : this.children)
		{
			child.render();
		}
	}

	public void render() {}

	public void destroy() {}

	public ArrayList<SceneNode> getChildren() {
		return this.children;
	}

	public boolean hasParent() {
		return this.parentHasBeenRegistered;
	}

	public void preRender() {
		glPushMatrix();
	}
	public void postRender() {
		glPopMatrix();
	}
}

