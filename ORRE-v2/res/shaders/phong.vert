varying vec3 LightDir[3];
varying vec3 Normal;
varying vec3 ViewDirection;

void main()
{	
	Normal = gl_NormalMatrix * gl_Normal;
	
	LightDir[0] = normalize(vec3(-gl_LightSource[0].position));
	LightDir[1] = normalize(vec3(-gl_LightSource[1].position));
	LightDir[2] = normalize(vec3(-gl_LightSource[2].position));

	ViewDirection = vec3(gl_ModelViewMatrixInverse[0][3], gl_ModelViewMatrixInverse[1][3], gl_ModelViewMatrixInverse[2][3]) - (gl_ModelViewMatrix * gl_Vertex).xyz;
				
	gl_Position = ftransform();
}