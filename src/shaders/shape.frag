#version 400 core

uniform sampler2D texID;
uniform int useTexture = 0;
uniform vec3 lightColor;

in vec2 vTexCoord;
in vec3 vNormal;
//vec3 vNormals = vec3(0,0,-1);
in vec3 toLightVector;

out vec4 color;

void main() {
	
	vec3 unitNormal = normalize(vNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDl = dot(unitNormal, unitLightVector);
	float brightness = max(nDl, 0.0);
	vec3 diffuse = brightness * lightColor;

	if(useTexture == 0) {
		color = vec4(0.5, 0.5, 0.5, 1.0);
	}
	else {
		color = texture2D(texID, vTexCoord.xy);
	}
	
	color = color * vec4(diffuse, 1.0);
}