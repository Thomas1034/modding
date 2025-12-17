#version 150

#moj_import <minecraft:projection.glsl>

in vec4 Position;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform ConvolutionConfig {
    vec2 ConvolveXDir;
    vec2 ConvolveYDir;
    vec3 KernelRow0;
    vec3 KernelRow1;
    vec3 KernelRow2;
};

out vec2 texCoord;
out vec2 sampleXStep;
out vec2 sampleYStep;

void main() {
    vec4 outPos = ProjMat * vec4(Position.xy * OutSize, 0.0, 1.0);
    gl_Position = vec4(outPos.xy, 0.2, 1.0);

    vec2 oneTexel = 1.0 / InSize;
    sampleXStep = oneTexel * ConvolveXDir;
    sampleYStep = oneTexel * ConvolveYDir;

    texCoord = Position.xy;
}
