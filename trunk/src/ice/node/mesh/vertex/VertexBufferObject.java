package ice.node.mesh.vertex;

import javax.microedition.khronos.opengles.GL11;
import java.nio.Buffer;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 11-11-21
 * Time: 下午12:04
 */
public class VertexBufferObject extends VertexData {


    public VertexBufferObject(int verticesCount, VertexAttributes attributes) {
        super(verticesCount, attributes);
        vboBuffer = new int[1];
    }


    @Override
    public void attach(GL11 gl) {
        if (!upload) {
            upload(gl);
        } else {
            gl.glBindBuffer(GL_ARRAY_BUFFER, vboBuffer[0]);
            if (subData != null) {
                gl.glBufferSubData(GL_ARRAY_BUFFER, subDataOffset, subDataSize, subData);
                subData = null;
            }
        }


        int textureUnit = 0;
        int numAttributes = attributes.size();

        for (int i = 0; i < numAttributes; i++) {

            VertexAttribute attribute = attributes.get(i);
            int offset = attribute.getOffset();
            int dimension = attribute.getDimension();

            switch (attribute.getUsage()) {
                case Position:
                    gl.glEnableClientState(GL_VERTEX_ARRAY);
                    gl.glVertexPointer(dimension, GL_FLOAT, attributes.vertexSize, offset);
                    break;

                case Color:
                    gl.glEnableClientState(GL_COLOR_ARRAY);
                    gl.glColorPointer(dimension, GL_FLOAT, attributes.vertexSize, offset);
                    break;

                case Normal:
                    gl.glEnableClientState(GL_NORMAL_ARRAY);
                    gl.glNormalPointer(GL_FLOAT, attributes.vertexSize, offset);
                    break;

                case TextureCoordinates:
                    gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);
                    gl.glClientActiveTexture(GL_TEXTURE0 + textureUnit);
                    gl.glTexCoordPointer(dimension, GL_FLOAT, attributes.vertexSize, offset);
                    textureUnit++;
                    break;

                default:
                    // throw new GdxRuntimeException("unkown vertex attribute type: " + attribute.usage);
            }
        }
    }

    public void postSubData(int offset, int size, Buffer subData) {
        if (this.subData != null) {
            System.out.println("Warning ! VBO subdata ignored !");
            return;
        }

        this.subDataOffset = offset;
        this.subDataSize = size;
        this.subData = subData;
    }

    private void upload(GL11 gl) {
        gl.glGenBuffers(vboBuffer.length, vboBuffer, 0);
        gl.glBindBuffer(GL_ARRAY_BUFFER, vboBuffer[0]);
        gl.glBufferData(GL_ARRAY_BUFFER, srcData.capacity(), srcData, GL_STATIC_DRAW);

        upload = true;
    }

    @Override
    public void release(GL11 gl) {
        gl.glDeleteBuffers(vboBuffer.length, vboBuffer, 0);
        upload = false;
    }


    private boolean upload;
    private int[] vboBuffer;
    private Buffer subData;
    private int subDataOffset, subDataSize;
}
