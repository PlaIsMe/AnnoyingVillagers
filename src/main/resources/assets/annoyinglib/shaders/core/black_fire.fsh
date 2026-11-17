#version 150

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
in vec4 vertexColor;
in vec2 texCoord0;
out vec4 fragColor;

void main() {
    // Preserve the original flame detail and soften the smoke silhouette.
    vec4 flame = texture(Sampler0, texCoord0);
    float alpha = smoothstep(0.025, 0.58, flame.a) * vertexColor.a * ColorModulator.a;
    if (alpha < 0.01) discard;
    float heat = dot(flame.rgb, vec3(0.299, 0.587, 0.114));
    // Continuous charcoal-to-white falloff instead of four posterized color bands.
    float shade = mix(0.012, 1.0, smoothstep(0.76, 0.94, heat));
    fragColor = vec4(vec3(shade) * vertexColor.rgb * ColorModulator.rgb, alpha);
}
