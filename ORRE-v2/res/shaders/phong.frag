uniform vec4 AmbientColor;
uniform vec4 DiffuseColor;
uniform vec4 SpecularColor;
uniform float Shininess;

varying vec3 LightDir[3];
varying vec3 Normal;
varying vec3 ViewDirection;

void main()
{
	vec3 n, halfV, lightDir, viewDir, reflection;
	float NdotL, RdotV;
	n = normalize(Normal);
	viewDir = normalize(ViewDirection);
	
	vec4 color = vec4(0.0, 0.0, 0.0, 0.0);
	
	//Light 0
	lightDir = normalize(-LightDir[0]);
	
	color += AmbientColor * gl_LightSource[0].ambient;
	color += gl_LightModel.ambient * AmbientColor;

	NdotL = max(dot(n, lightDir),0.0);
	color += DiffuseColor * gl_LightSource[0].diffuse * NdotL;

	reflection = -reflect(lightDir, n);
	RdotV = max( 0.0, dot( reflection, viewDir ) );
	color += SpecularColor * gl_LightSource[0].specular * pow(RdotV, Shininess);


	//Light 1
	lightDir = normalize(-LightDir[1]);
	
	color += AmbientColor * gl_LightSource[1].ambient;
	color += gl_LightModel.ambient * AmbientColor;

	NdotL = max(dot(n, lightDir),0.0);
	color += DiffuseColor * gl_LightSource[1].diffuse * NdotL;

	reflection = -reflect(lightDir, n);
	RdotV = max( 0.0, dot( reflection, viewDir ) );
	color += SpecularColor * gl_LightSource[1].specular * pow(RdotV, Shininess);


	//Light 2
	lightDir = normalize(-LightDir[2]);
	
	color += AmbientColor * gl_LightSource[2].ambient;
	color += gl_LightModel.ambient * AmbientColor;

	NdotL = max(dot(n, lightDir),0.0);
	color += DiffuseColor * gl_LightSource[2].diffuse * NdotL;

	reflection = -reflect(lightDir, n);
	RdotV = max( 0.0, dot( reflection, viewDir ) );
	color += SpecularColor * gl_LightSource[2].specular * pow(RdotV, Shininess);

	color.w = DiffuseColor.w;
	gl_FragColor = color;
	
}