package orre.resources.incompleteResources;

// Only meant as a means of keeping track of an incomplete geometry buffer which
// makes use of a particular material. Not really a separate concept or entity.
public class IncompleteGeometryMaterialCombo {
	public final IncompleteGeometryBuffer geometryBuffer;
	public final IncompleteBlueprintMaterial material;
	
	public IncompleteGeometryMaterialCombo(IncompleteGeometryBuffer geometryBuffer, IncompleteBlueprintMaterial material) {
		this.geometryBuffer = geometryBuffer;
		this.material = material;
	}
}
