package cn.funcoding.opengles.sample.google.triangle1;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class Triangle {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;

    // 此数组中每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // 按逆时针顺序:
            0.0f, 0.622008459f, 0.0f, // 上
            -0.5f, -0.311004243f, 0.0f, // 左下
            0.5f, -0.311004243f, 0.0f  // 右下
    };

    // 使用红色、绿色、蓝色和 alpha（不透明度）值设置颜色
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final int mProgram;

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点 4 个字节

    public Triangle() {
        // 为形状坐标初始化顶点字节缓冲区
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (坐标值个数 * 4 bytes per float)
                triangleCoords.length * 4);
        // 使用设备硬件的本机字节顺序
        bb.order(ByteOrder.nativeOrder());

        // 从 ByteBuffer 创建一个浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();
        // 将坐标添加到 FloatBuffer
        vertexBuffer.put(triangleCoords);
        // 设置缓冲区读取第一个坐标
        vertexBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 创建空的 OpenGL ES 程序
        mProgram = GLES20.glCreateProgram();

        // 将顶点着色器添加到程序中
        GLES20.glAttachShader(mProgram, vertexShader);

        // 将片段着色器添加到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);

        // 创建 OpenGL ES 程序可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    public void draw() {
        // 将程序添加到 OpenGL ES 环境
        GLES20.glUseProgram(mProgram);

        // 获取顶点着色器的 vPosition 成员句柄
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(positionHandle);

        // 准备三角坐标数据
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 获取片段着色器的 vColor 成员句柄
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 设置绘制三角形的颜色
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // 画出三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
    