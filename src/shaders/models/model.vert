#version 400 core

in vec3 vertex;
in vec3 normal;
in vec2 texCoord;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;
uniform vec3 lightPos;

out vec3 vNormal;
out vec2 vTexCoord;
out vec3 toLightVector;
out vec3 toCameraVector;

void main() {
	vec4 position = vec4(vertex, 1.0) * ml_matrix;
	gl_Position =  position * vw_matrix * pr_matrix;
	
	toLightVector = lightPos - position.xyz;
	toCameraVector = (inverse(vw_matrix) * vec4(0.0,0.0,0.0,1.0)).xyz - position.xyz;
	
	vTexCoord = texCoord;
	vNormal = (vec4(normal, 1.0) * ml_matrix).xyz;
}