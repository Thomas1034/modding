#version 150

uniform sampler2D InSampler;

layout(std140) uniform ConvolutionConfig {
    vec2 ConvolveXDir;
    vec2 ConvolveYDir;
    vec3 KernelRow0;
    vec3 KernelRow1;
    vec3 KernelRow2;
};


in vec2 texCoord;
in vec2 sampleXStep;
in vec2 sampleYStep;

out vec4 fragColor;

void main() {

    // 3-tap convolution
    vec3 k0 = KernelRow0;
    vec3 k1 = KernelRow1;
    vec3 k2 = KernelRow2;

    vec4 sum = vec4(0.0);
    sum += texture(InSampler, texCoord + (-1 * sampleXStep + -1 * sampleYStep)) * k0.x;
    sum += texture(InSampler, texCoord + (0 * sampleXStep + -1 * sampleYStep)) * k0.y;
    sum += texture(InSampler, texCoord + (1 * sampleXStep + -1 * sampleYStep)) * k0.z;

    sum += texture(InSampler, texCoord + (-1 * sampleXStep + 0 * sampleYStep)) * k1.x;
    sum += texture(InSampler, texCoord + (0 * sampleXStep + 0 * sampleYStep)) * k1.y;
    sum += texture(InSampler, texCoord + (1 * sampleXStep + 0 * sampleYStep)) * k1.z;

    sum += texture(InSampler, texCoord + (-1 * sampleXStep + 1 * sampleYStep)) * k2.x;
    sum += texture(InSampler, texCoord + (0 * sampleXStep + 1 * sampleYStep)) * k2.y;
    sum += texture(InSampler, texCoord + (1 * sampleXStep + 1 * sampleYStep)) * k2.z;

    fragColor = sum;
}