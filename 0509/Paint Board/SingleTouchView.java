package kr.co.example.singletouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Stack;

public class SingleTouchView extends View {
    // 현재 사용 중인 Paint 객체
    private Paint currentPaint;

    // 경로와 페인트를 저장할 리스트
    private ArrayList<PathWithPaint> paths = new ArrayList<>();

    // 실행 취소된 경로를 저장할 스택
    private Stack<PathWithPaint> undoPaths = new Stack<>();

    // 현재 그리고 있는 경로
    private Path currentPath;

    private float lastX, lastY;

    // 지우개 모드 플래그
    private boolean eraserMode = false;

    // 펜 크기와 지우개 크기를 별도로 저장
    private float penSize = 10f;
    private float eraserSize = 20f;

    // 경로와 페인트를 함께 저장하는 클래스
    private static class PathWithPaint {
        public Path path;
        public Paint paint;

        PathWithPaint(Path path, Paint paint) {
            this.path = path;
            this.paint = new Paint(paint); // 깊은 복사
        }
    }

    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 하드웨어 가속 비활성화 (PorterDuff.Mode.CLEAR가 하드웨어 가속과 함께 작동하지 않을 수 있음)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 초기 페인트 설정
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setStrokeWidth(penSize);
        currentPaint.setColor(Color.BLUE);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);

        // 초기 경로 생성
        currentPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 캔버스를 저장하고 새 레이어에 그리기
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        // 저장된 모든 경로 그리기
        for (PathWithPaint pp : paths) {
            canvas.drawPath(pp.path, pp.paint);
        }

        // 현재 그리고 있는 경로 그리기
        canvas.drawPath(currentPath, currentPaint);

        // 레이어 복원
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 새로운 경로 시작
                currentPath = new Path();
                currentPath.moveTo(x, y);
                lastX = x;
                lastY = y;
                return true;

            case MotionEvent.ACTION_MOVE:
                currentPath.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
                lastX = x;
                lastY = y;
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                currentPath.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);

                // 현재 경로와 페인트 복사하여 저장
                Path pathCopy = new Path(currentPath);
                paths.add(new PathWithPaint(pathCopy, currentPaint));

                // 새로운 경로를 추가하면 실행 취소 스택 비우기
                undoPaths.clear();

                // 현재 경로 초기화
                currentPath = new Path();
                invalidate();
                return true;
        }
        return false;
    }

    // 색상 변경 메소드 - 현재 페인트의 색상만 변경
    public void setColor(int color) {
        eraserMode = false;
        currentPaint = new Paint(currentPaint);
        currentPaint.setColor(color);
        // 지우개 모드가 아닐 때는 Xfermode 제거
        currentPaint.setXfermode(null);
        // 펜 크기로 설정
        currentPaint.setStrokeWidth(penSize);
    }

    // 펜 크기 설정 메소드
    public void setPenSize(float size) {
        penSize = size;
        if (!eraserMode) {
            currentPaint = new Paint(currentPaint);
            currentPaint.setStrokeWidth(penSize);
        }
    }

    // 지우개 크기 설정 메소드
    public void setEraserSize(float size) {
        eraserSize = size;
        if (eraserMode) {
            currentPaint = new Paint(currentPaint);
            currentPaint.setStrokeWidth(eraserSize);
        }
    }

    // 지우개 모드 설정 메소드
    public void setEraserMode(boolean isEraserMode) {
        eraserMode = isEraserMode;
        currentPaint = new Paint(currentPaint);

        if (isEraserMode) {
            // 지우개 모드: PorterDuff.Mode.CLEAR 사용
            currentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            currentPaint.setStrokeWidth(eraserSize);
        } else {
            // 그리기 모드: Xfermode 제거
            currentPaint.setXfermode(null);
            currentPaint.setStrokeWidth(penSize);
        }

        // 선 끝과 연결 부분을 둥글게 설정
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    // 화면 지우기 메소드
    public void clearCanvas() {
        paths.clear();
        undoPaths.clear();
        currentPath = new Path();
        invalidate();
    }

    // 실행 취소 메소드
    public boolean undo() {
        if (paths.size() > 0) {
            PathWithPaint removed = paths.remove(paths.size() - 1);
            undoPaths.push(removed);
            invalidate();
            return true;
        }
        return false;
    }

    // 다시 실행 메소드
    public boolean redo() {
        if (!undoPaths.empty()) {
            PathWithPaint redoPath = undoPaths.pop();
            paths.add(redoPath);
            invalidate();
            return true;
        }
        return false;
    }

    // 현재 펜 크기 반환
    public float getPenSize() {
        return penSize;
    }

    // 현재 지우개 크기 반환
    public float getEraserSize() {
        return eraserSize;
    }
}
