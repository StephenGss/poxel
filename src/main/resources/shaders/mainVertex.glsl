#version 460 core

in vec3 position;
in vec3 color;
in vec2 textureCoord;
in vec3 normal;

out vec3 passColor;
out vec2 passTextureCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 transform;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 lightPosition;

void main() {
    vec4 worldPosition = transform * vec4(position,1.0);

	gl_Position = projection * view * transform * vec4(position, 1.0);
	passColor = color;
	passTextureCoord = textureCoord;

	surfaceNormal = (transform * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPosition.xyz;
	toCameraVector = (inverse(view) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
}