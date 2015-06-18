#version 400 core

uniform sampler2D texID;
uniform vec3 lightColor;

in vec2 vTexCoord;
in vec3 vNormal;
in vec3 toLightVector;

out vec4 color;

void main() {
	
	vec3 unitNormal = normalize(vNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDl = dot(unitNormal, unitLightVector);
	float brightness = max(nDl, 0.00);
	vec3 diffuse = brightness * lightColor;
	
	color = texture(texID, vTexCoord.xy) * vec4(diffuse, 1.0);
}