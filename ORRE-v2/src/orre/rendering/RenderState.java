package orre.rendering;

public class RenderState {
	public final TransformationsRenderState transformations = new TransformationsRenderState();
	public final ShaderRenderState shaders = new ShaderRenderState(this);
}
