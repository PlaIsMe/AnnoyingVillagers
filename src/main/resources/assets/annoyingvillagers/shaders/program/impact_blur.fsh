#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 center;

uniform float intensity;
uniform float strength;
uniform int samples;

in vec2 texCoord;
out vec4 fragColor;

void main()
{
    vec2 dir = texCoord - center;

    float dist = length(dir);
    float distFactor = smoothstep(0.0, 0.8, dist);
    float blurBoost = mix(0.2, 1.5, distFactor);
    vec2 ndir = vec2(0.0);

    if (dist > 0.00001){
        ndir = dir / dist;
    }
    float blast = (1.0 - intensity);
    float core = 1.0 - smoothstep(0.0, 0.4, dist);


    vec2 offset = ndir * strength * blast * blurBoost * distFactor * (1.0 + core * 1.5);
    vec4 color = texture(DiffuseSampler, texCoord);
    float total = 1.0;

    for (int i = 1; i <= samples; i++){
        float t = float(i) / float(samples);
        float weight = pow(1.0 - t, 2.0);
        vec2 uv = texCoord - offset * t;
        uv = clamp(uv, vec2(0.001), vec2(0.999));
        color += texture(DiffuseSampler, uv) * weight;
        total += weight;
    }

    color.rgb += core * blast * 0.25;
    fragColor = color / total;
}
