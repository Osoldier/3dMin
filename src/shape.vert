#version 120

attribute vec3 vertex;
attribute vec3 normal;
attribute vec2 texCoord;

uniform mat4 ml_matrix;
uniform mat4 vw_matrix;
uniform mat4 pr_matrix;

varying vec3 vNormal;
varying vec2 vTexCoord;

void main() {
	gl_Position = vec4(vertex, 1.0) * ml_matrix * vw_matrix * pr_matrix;
	vNormal = normal;
	vTexCoord = texCoord;
}