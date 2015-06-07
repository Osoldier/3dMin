#version 400 core

in vec3 vertex;
in vec2 texCoord;

uniform mat4 pr_matrix;

out vec2 vTexCoord;

void main() {
	gl_Position =  vec4(vertex, 1.0) * pr_matrix;
	vTexCoord = texCoord;
}