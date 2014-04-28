// input
varying vec3 normal; // fragment normal in eye space.
varying vec3 position; // fragment position in eye space.

uniform sampler2D diffuseTexture;

vec4 lightSource( vec3 N, vec3 V, gl_LightSourceParameters light )
{
	vec3  H;
	float d = length( light.position.xyz - V );
	vec3  L = normalize( light.position.xyz - V );
	H = normalize( L - V.xyz );

	float NdotL = max( 0, dot( N,L ) );
	float NdotH = max( 0, dot( N,H ) );

	float Idiff = NdotL;
	float Ispec = pow( NdotH, gl_FrontMaterial.shininess );

	// 'real' shading
	return 
		gl_FrontMaterial.ambient  * light.ambient +
		gl_FrontMaterial.diffuse  * light.diffuse  * Idiff +
		gl_FrontMaterial.specular * light.specular * Ispec;
}

vec4 lighting( void )
{
	// normal might be damaged by linear interpolation.
	vec3 N = normalize(normal);

	return
		gl_FrontMaterial.emission +
		gl_FrontMaterial.ambient * gl_LightModel.ambient +
		lightSource( N, position, gl_LightSource[0] );
}

void main( void )
{
	gl_FragColor = texture2D(diffuseTexture, gl_TexCoord[0].st) * lighting();
}