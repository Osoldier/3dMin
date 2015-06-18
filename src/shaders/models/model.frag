#version 400 core

uniform sampler2D texID;
uniform int useTexture = 0;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

in vec2 vTexCoord;
in vec3 vNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 color;

void main() {
	
	vec3 unitNormal = normalize(vNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDl = dot(unitNormal, unitLightVector);
	float brightness = max(nDl, 0.05);
	vec3 diffuse = brightness * lightColor;
	
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 lightDir = -unitLightVector;
	vec3 reflectedLightDir = reflect(lightDir, unitNormal);
	
	float specularFactor = dot(reflectedLightDir, unitVectorToCamera);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = lightColor * reflectivity * dampedFactor;

	if(useTexture == 0) {
		color = vec4(0.5, 0.5, 0.5, 1.0);
	}
	else {
		color = texture(texID, vTexCoord.xy);
	}
	if(color.a < 0.5) {
		discard;
	}
	color = vec4(diffuse, 1.0) * color + vec4(finalSpecular, 1.0);
}