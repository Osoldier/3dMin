#version 330 core

layout (location = 0) in vec3 vertex;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;

void main() {
	gl_Position = vec4(vertex, 1.0) * ml_matrix * vw_matrix * pr_matrix;
}