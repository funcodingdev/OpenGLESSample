package cn.funcoding.opengles.sample.google.triangle2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 使用 OpenGL ES 2.0 绘制定义的形状需要大量代码，因为您必须向图形渲染管道提供大量详细信息。具体来说，您必须定义以下内容：
 * <p>
 * 顶点着色程序 - 用于渲染形状的顶点的 OpenGL ES 图形代码。
 * 片段着色程序 - 用于使用颜色或纹理渲染形状面的 OpenGL ES 代码。
 * 程序 - 包含您希望用于绘制一个或多个形状的着色程序的 OpenGL ES 对象。
 */
class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "MyGLRenderer";
    private Triangle mTriangle;
    private Square mSquare;

    // vPMatrix是"Model View Projection Matrix（模型视图投影矩阵）"的缩写
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    /**
     * 调用一次以设置视图的 OpenGL ES 环境时调用
     *
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置背景框颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();
    }

    /**
     * 当视图的几何图形发生变化（例如当设备的屏幕方向发生变化）时调用
     *
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "onSurfaceChanged: width=" + width + ", height=" + height);
        GLES20.glViewport(0, 0, width, height);
        // 屏幕比率
        float ratio = (float) width / height;

        // 此投影矩阵应用于对象坐标
        // in the onDrawFrame() method
        // 注意：仅将投影转换应用于绘制的对象通常会导致显示画面过于空旷。一般而言，要在屏幕上显示任何内容，您还必须应用相机视图转换
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    /**
     * 每次重新绘制视图时调用
     *
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        // 重绘背景颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        // Draw shape
        mTriangle.draw(vPMatrix);
    }

    /**
     * 着色程序包含 OpenGL 着色语言 (GLSL) 代码，必须先对其进行编译，然后才能在 OpenGL ES 环境中使用
     * 注意：就 CPU 周期和处理时间而言，编译 OpenGL ES 着色程序及关联程序的成本非常高，因此应避免多次执行该操作。如果您在运行时不清楚着色程序的内容，则应编译代码，使其仅被创建一次，然后缓存以备后用
     *
     * @param type
     * @param shaderCode
     * @return
     */
    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
