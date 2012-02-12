package orre.resources.loaders;

public class ModelPart {
	
	public final ModelPartType partType;
	public final String name;

	public ModelPart(ModelPartType partType, String name)
	{
		this.partType = partType;
		this.name = name;
	}
}

enum ModelPartType {
	PHYSICAL, VIRTUAL
}
