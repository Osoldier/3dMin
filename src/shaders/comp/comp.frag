#version 400 core

uniform sampler2D texID;

in vec2 vTexCoord;

out vec4 color;

void main() {
	color = texture2D(texID, vTexCoord.xy);
}