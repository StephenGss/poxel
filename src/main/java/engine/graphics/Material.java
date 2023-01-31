package engine.graphics;

import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryStack;
import java.nio.*;
import java.io.*;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.stb.STBImageResize.*;
import static org.lwjgl.system.MemoryUtil.*;
import static engine.io.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.stb.STBImage.*;

public class Material {
    private ByteBuffer texture;
    private int width, height, comp, textureID;

    public Material(String path){
        // load in image
        ByteBuffer imageBuffer;
        try {
            imageBuffer = ioResourceToByteBuffer(path, 8 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(MemoryStack stack = stackPush()){
            IntBuffer w    = stack.mallocInt(1);
            IntBuffer h    = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);
            // Decode the image
            texture = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
            if (texture == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            this.width = w.get(0);
            this.height = h.get(0);
            this.comp = comp.get(0);
        }

    }

    public void create(){
        textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        int format;
        if (comp == 3) {
            if ((width & 3) != 0) {
                glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
            }
            format = GL_RGB;
        } else {
            premultiplyAlpha();

            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

            format = GL_RGBA;
        }

        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, texture);

        ByteBuffer input_pixels = texture;
        int        input_w      = width;
        int        input_h      = height;
        int        mipmapLevel  = 0;
        while (1 < input_w || 1 < input_h) {
            int output_w = Math.max(1, input_w >> 1);
            int output_h = Math.max(1, input_h >> 1);

            ByteBuffer output_pixels = memAlloc(output_w * output_h * comp);
            stbir_resize_uint8_generic(
                    input_pixels, input_w, input_h, input_w * comp,
                    output_pixels, output_w, output_h, output_w * comp,
                    comp, comp == 4 ? 3 : STBIR_ALPHA_CHANNEL_NONE, STBIR_FLAG_ALPHA_PREMULTIPLIED,
                    STBIR_EDGE_CLAMP,
                    STBIR_FILTER_MITCHELL,
                    STBIR_COLORSPACE_SRGB
            );

            if (mipmapLevel == 0) {
                stbi_image_free(texture);
            } else {
                memFree(input_pixels);
            }

            glTexImage2D(GL_TEXTURE_2D, ++mipmapLevel, format, output_w, output_h, 0, format, GL_UNSIGNED_BYTE, output_pixels);

            input_pixels = output_pixels;
            input_w = output_w;
            input_h = output_h;
        }
        if (mipmapLevel == 0) {
            stbi_image_free(texture);
        } else {
            memFree(input_pixels);
        }
    }

    private void premultiplyAlpha() {
        int stride = width * 4;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = y * stride + x * 4;

                float alpha = (texture.get(i + 3) & 0xFF) / 255.0f;
                texture.put(i + 0, (byte)round(((texture.get(i + 0) & 0xFF) * alpha)));
                texture.put(i + 1, (byte)round(((texture.get(i + 1) & 0xFF) * alpha)));
                texture.put(i + 2, (byte)round(((texture.get(i + 2) & 0xFF) * alpha)));
            }
        }
    }

    public void destroy(){
        GL13.glDeleteTextures(textureID);
    }

    public ByteBuffer getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
