#version 150

uniform float x, y, zoom;
uniform int width, height, iteration;

out vec4 fragColor;

void main() {
    vec2 z, c;

    c.x = (((gl_FragCoord.x - 0.5) - width / 2) / zoom) + x;
    c.y = (((gl_FragCoord.y - 0.5) - height / 2) / zoom) + y;

    int i;
    z = c;

    float x = 0;
    float y = 0;

    for(i=0; i<iteration; i++)
    {
        x = (z.x * z.x - z.y * z.y) + c.x;
//        y = (2 * z.x * z.y) + c.y;
        y = (z.y * z.x + z.x * z.y) + c.y;

        if((x * x + y * y) > 4.0)
            break;

        z.x = x;
        z.y = y;
    }

    fragColor = (i == iteration ? vec4(0,0,0,0) : vec4(float(i), float(i), float(i), 0));
}