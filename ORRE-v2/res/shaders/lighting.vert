#version 120

varying vec3 normal;   // vertex normal in eye space.
varying vec3 position; // vertex position in eye space.

void main( void )
{
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_TexCoord[0] = gl_MultiTexCoord0;

	normal = normalize(gl_NormalMatrix * gl_Normal);
	position = ( gl_ModelViewProjectionMatrix * gl_Vertex ).xyz;
}


