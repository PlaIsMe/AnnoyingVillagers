#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

void main() {
    // The reused aura has an opaque black background: luminance supplies its transparency.
    vec3 texel = texture(Sampler0, fract(texCoord0)).rgb;
    float energy = dot(texel, vec3(0.299, 0.587, 0.114));
    float edge = 1.0 - smoothstep(0.12, 0.5, abs(texCoord0.x - 0.5));
    float alpha = edge * (0.14 + 0.86 * smoothstep(0.18, 0.78, energy)) * vertexColor.a * ColorModulator.a;
    if (alpha < 0.01) discard;
    vec3 hot = mix(vertexColor.rgb, vec3(1.0, 1.0, 0.7), smoothstep(0.75, 1.0, energy));
    fragColor = vec4(hot * ColorModulator.rgb, alpha);
}
