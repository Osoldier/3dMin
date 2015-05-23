#version 120

uniform sampler2D texID;
uniform int useTexture = 0;

varying vec2 vTexCoord;
varying vec3 vNormals;

void main() {
	if(useTexture == 0) {
		gl_FragColor = vec4(0.5, 0.5, 0.5, 1.0);
	}
	else {
		gl_FragColor = texture2D(texID, vTexCoord.xy);
	}
}