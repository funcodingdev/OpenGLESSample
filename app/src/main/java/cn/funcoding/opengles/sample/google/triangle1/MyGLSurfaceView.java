package cn.funcoding.opengles.sample.google.triangle1;

import android.content.Context;
import android.opengl.GLSurfaceView;

class MyGLSurfaceView extends GLSurfaceView {
    private MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        // 设置OpenGL ES 2.0上下文
        setEGLContextClientVersion(2);
        renderer = new MyGLRenderer();
        // 设置GLSurfaceView的渲染器
        setRenderer(renderer);
        // 设置将渲染模式设置为仅在绘制数据发生变化时绘制视图
        // 该设置可防止系统在您调用 requestRender() 之前重新绘制 GLSurfaceView 帧
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
