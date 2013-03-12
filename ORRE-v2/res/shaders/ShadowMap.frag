#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 smPos01;
varying vec4 viewPos01;
varying vec3 normal;
varying vec3 worldPos;
varying vec4 Color;

//uniforms
uniform sampler2D DepthMap;
uniform vec4 LightPosition;

//not needed and only for educational purposes
uniform sampler2D ViewMap;

void main(void)
{
	


	float light=max(0.0,dot(normalize(LightPosition.xyz-worldPos),normalize(normal.xyz)));

	
	//this is an additional test that only serves educational purposes...		
	vec3 realViewPos=viewPos01.xyz/viewPos01.w;
	/*if (
		(realViewPos.x<0||realViewPos.x>1)
		||(realViewPos.y<0||realViewPos.y>1)
		)
		{
			//outside the view frustum
			light=light*0.99;
		}
	else
	{
		float depthView = texture2D(ViewMap, realViewPos.xy).r;
		if (depthView < realViewPos.z-0.001)
		{		
			light=light*0.99;
		}
	}*/
		
	//this is the actual shadow mapping (including the magic bias)!
	vec3 realSmPos=smPos01.xyz/smPos01.w;	
	float depthSm = texture2D(DepthMap, realSmPos.xy).r;
	if (depthSm < realSmPos.z-0.001)
	{		
		light=0;
	}
		
	gl_FragColor=light;
}

 





#if 0


void main(void)
{
	vec4 colorValue=vec4(0,1,0,1);;

	
	vec3 realSmPos=smPos01.xyz/smPos01.w;
	vec3 realViewPos=viewPos01.xyz/viewPos01.w;
	
	float depthSm = texture2D(DepthMap, realSmPos.xy).r;

	float frustum=1;

	float light=max(0.0,dot(LightPosition.xyz-worldPos,normalize(normal.xyz)))+0.2;

	
	if (
		(realViewPos.x<0||realViewPos.x>1)
		||(realViewPos.y<0||realViewPos.y>1)
		)
		{
			colorValue=vec4(1,0,1,1);
			frustum=0;
			light=0.9;
		}
	else
	{
			
		float depthView = texture2D(ViewMap, realViewPos.xy).r;
		if (depthView < realViewPos.z)
		{		
			colorValue=vec4(1,0,1,1);
			light=0.9;
		}
	}


	
	if (depthSm>realSmPos.z)
		gl_FragColor=light*colorValue;
	else
		gl_FragColor=0.6*colorValue;
		
		
	//gl_FragColor=vec4(depthSm, depthSm, ,1);
}

 





#endif









#if 0


#extension GL_EXT_gpu_shader4 : enable 
#extension GL_ARB_draw_buffers : enable

//INPUTS
//these are standard shadow map coordinates
varying vec4 smPos01;
varying vec4 viewPos01;
varying vec3 normal;
varying vec3 worldPos;

//uniforms
uniform sampler2D DepthMap;
uniform sampler2D ViewMap;
uniform vec4 LightPosition;

void main(void)
{
	vec4 colorValue=vec4(0,1,0,1);;

	
	vec3 realSmPos=smPos01.xyz/smPos01.w;
	vec3 realViewPos=viewPos01.xyz/viewPos01.w;
	
	float depthSm = texture2D(DepthMap, realSmPos.xy).r;

	float frustum=1;

	float light=max(0.0,dot(LightPosition.xyz-worldPos,normalize(normal.xyz)))+0.2;

	
	if (
		(realViewPos.x<0||realViewPos.x>1)
		||(realViewPos.y<0||realViewPos.y>1)
		)
		{
			colorValue=vec4(1,1,1,1);
		}
	else
	{
			
		float depthView = texture2D(ViewMap, realViewPos.xy).r;
		if (depthView < realViewPos.z)
		{		
			colorValue=vec4(1,0,1,1);
		}
	}


	if (
		(realSmPos.x<0||realSmPos.x>1)
		||(realSmPos.y<0||realSmPos.y>1)
		)
		{
			colorValue=vec4(1,1,1,1);
			light=0;
			frustum=0;
		}
	
	if (depthSm>realSmPos.z)
		gl_FragColor=light*colorValue;
	else
		gl_FragColor=light*0.6*colorValue;
		
		
	//gl_FragColor=vec4(depthSm, depthSm, ,1);
}

 
#endif



#if 0


void main(void)
{
	vec4 colorValue=vec4(0,1,0,1);;

	
	vec3 realSmPos=smPos01.xyz/smPos01.w;
	vec3 realViewPos=viewPos01.xyz/viewPos01.w;
	
	float depthSm = texture2D(DepthMap, realSmPos.xy).r;

	float frustum=1;

	float light=max(0.0,dot(LightPosition.xyz-worldPos,normalize(normal.xyz)))+0.2;

	
	if (
		(realViewPos.x<0||realViewPos.x>1)
		||(realViewPos.y<0||realViewPos.y>1)
		)
		{
			colorValue=vec4(1,0,1,1);
			frustum=0;
			light=0.9;
		}
	else
	{
			
		float depthView = texture2D(ViewMap, realViewPos.xy).r;
		if (depthView < realViewPos.z)
		{		
			colorValue=vec4(1,0,1,1);
			light=0.9;
		}
	}


	
	if (depthSm>realSmPos.z)
		gl_FragColor=light*colorValue;
	else
		gl_FragColor=0.6*colorValue;
		
		
	//gl_FragColor=vec4(depthSm, depthSm, ,1);
}

 









#endif