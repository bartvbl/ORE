/* lighting.vert - emulates fixed function pipeline lighting */


varying vec3 normal;   // vertex normal in eye space.
varying vec3 position; // vertex position in eye space.
varying float depth;



//
// entry point
//
void main( void )
{
	gl_Position = ftransform();
	gl_TexCoord[0] = gl_MultiTexCoord0;

	normal = gl_NormalMatrix * gl_Normal;
	position = ( gl_ModelViewMatrix * gl_Vertex ).xyz;

	depth = position.z;
}


