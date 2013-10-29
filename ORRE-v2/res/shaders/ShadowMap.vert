#version 120
//OUTPUTS
uniform mat4x4 LightMatrixValue;
uniform mat4x4 ViewMatrixValue;
varying vec4 smPos01;
varying vec4 viewPos01;
varying vec3 normal;
varying vec3 worldPos;
varying vec4 color;

//VERTEXPROGRAM
void main(void)
{
	//we recover the position in the slice camera space.
	//vec4 modelPos=gl_ModelViewMatrix * gl_Vertex;
	vec4 modelPos=gl_Vertex;
	worldPos=modelPos.xyz/modelPos.w;

	vec4 lightPos=LightMatrixValue*modelPos;
	vec4 viewPos=gl_ModelViewMatrix*modelPos;

	normal = gl_Normal;
	color=gl_Color;
	
	//and we get the xy position. This allows the lookup in the local case
	//neat trick to get the -1, 1 range into 0,1 as is needed for the lookup in the texture
	smPos01 = 0.5 * (lightPos.xyzw +lightPos.wwww);
	viewPos01 = 0.5 * (viewPos.xyzw +viewPos.wwww);
	gl_Position=ftransform();
}

