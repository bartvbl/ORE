package orre.sceneGraph;

public class SceneRootNode extends ContainerNode {

	// The root node should add children in reverse order to keep the GUI at the end
	
	public SceneRootNode(String name) {
		super(name);
	}
	
	public void addGUINode(SceneNode node) {
		children.add(node);
	}
	
	@Override
	public void addChild(SceneNode node) 
	{
		this.children.add(0, node);
	}

	@Override
	public void removeChild(SceneNode node) 
	{
		this.children.remove(node);
	}
	
}
