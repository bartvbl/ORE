varying vec3 normal;
varying vec3 position;

void main( void )
{
	gl_Position = ftransform();
	gl_TexCoord[0] = gl_MultiTexCoord0;

	normal = gl_NormalMatrix * gl_Normal;
	position = ( gl_ModelViewMatrix * gl_Vertex ).xyz;
}