varying vec3 normal;
varying vec3 position;

uniform sampler2D diffuseTexture;
uniform float texturesEnabled;

vec4 lightSource(vec3 norm, vec3 view, gl_LightSourceParameters light)
{
	vec3 lightVector = normalize(light.position.xyz - view);
	vec3 reflection = normalize(lightVector - normalize(view.xyz));

	float diffuseFactor = max(0, dot(norm, lightVector));
	float specularDot = max(0, dot(norm, reflection));

	float specularFactor = pow(specularDot, gl_FrontMaterial.shininess);
	
	return 
		gl_FrontMaterial.ambient * light.ambient +
		gl_FrontMaterial.diffuse * light.diffuse * diffuseFactor +
		gl_FrontMaterial.specular * light.specular * specularFactor;
}

vec4 lighting()
{
	// normal might be damaged by linear interpolation.
	vec3 norm = normalize(normal);

	return
		gl_FrontMaterial.emission +
		gl_FrontMaterial.ambient * gl_LightModel.ambient +
		lightSource(norm, position, gl_LightSource[0]);
}

void main()
{
	vec4 lightValue = lighting();
	gl_FragColor = (1 * texture2D(diffuseTexture, gl_TexCoord[0].st) * lightValue) + ((1.0 - 1) * lightValue);
}