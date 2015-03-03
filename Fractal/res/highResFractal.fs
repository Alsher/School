#version 410

uniform sampler2D tex;

uniform double xOffset;
uniform double yOffset;
uniform double zoom;

uniform int width;
uniform int height;
uniform int iteration;
uniform int textureBool;

out vec4 fragColor;

void main()
{
    double z1, z2;
    double c1, c2;

    c1 = (((gl_FragCoord.x - 0.5) - width / 2) / zoom) + xOffset;
    c2 = (((gl_FragCoord.y - 0.5) - height / 2) / zoom) + yOffset;

    int i;
    z1 = c1;
    z2 = c2;

    double x = 0;
    double y = 0;

    for(i=0; i<iteration; i++)
    {
        x = (z1 * z1 - z2 * z2) + c1;
        y = (2 * z1 * z2) + c2;

        if((x * x + y * y) > 4.0)
            break;

        z1 = x;
        z2 = y;
    }

    if(textureBool == 1)
        fragColor = texture(tex, vec2(((i == iteration ? 0.0 : float(i)) / 100.0), 1.0));
    else
        fragColor = vec4((i == iteration ? 0.0 : float(i)), (i == iteration ? 0.0 : float(i)), (i == iteration ? 0.0 : float(i)), 1);
}