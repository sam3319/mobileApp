## 안드로이드 스튜디오에서 그래픽 처리

**1. 그래픽 처리의 기본 구조**

안드로이드에서 그래픽을 직접 그리려면 보통 `View`를 상속받아 커스텀 뷰를 만들고, 그 안에서 `onDraw(Canvas canvas)` 메서드를 오버라이드한다. 이 메서드에서 다양한 도형, 이미지, 텍스트 등을 그릴 수 있다.

- **Canvas**: 실제로 그리기를 담당하는 객체로, 선, 원, 사각형, 텍스트, 비트맵 등 다양한 그래픽 요소를 그릴 수 있다.
- **Paint**: 그리기 속성(색상, 두께, 스타일 등)을 지정하는 객체

예시 코드:

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint paint = new Paint();
    paint.setColor(Color.RED);
    canvas.drawCircle(100, 100, 50, paint); // 빨간 원 그리기
}
```

- 다양한 도형(`drawLine`, `drawRect`, `drawCircle`, `drawPath` 등)과 텍스트(`drawText`)를 그릴 수 있다.

**2. 이미지 처리 및 변환**

이미지(비트맵)는 `canvas.drawBitmap()`으로 그릴 수 있으며, 캔버스의 변환 메서드(`rotate`, `scale`, `translate`, `skew`)를 이용해 회전, 확대/축소, 이동, 기울이기 등의 효과를 줄 수 있다.

```java
canvas.rotate(45, centerX, centerY); // 45도 회전
canvas.drawBitmap(bitmap, x, y, null);
canvas.scale(2, 2, centerX, centerY); // 2배 확대
canvas.drawBitmap(bitmap, x, y, null);
```

비트맵에 직접 그려서 재사용하거나, OpenGL을 활용한 고성능 그래픽 처리도 가능하다.

---

## 안드로이드 터치 이벤트 처리

**1. 터치 이벤트의 기본 구조**

안드로이드에서 터치 이벤트는 `MotionEvent` 객체로 전달되며, 대표적으로 다음 세 가지 액션이 있다.

- `ACTION_DOWN`: 손가락이 화면에 닿았을 때
- `ACTION_MOVE`: 손가락을 움직일 때 (여러 번 발생)
- `ACTION_UP`: 손가락을 뗐을 때

이벤트 처리는 주로 `onTouchEvent(MotionEvent event)`를 오버라이드해서 구현한다.

예시 코드:

```java
@Override
public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // 터치 시작
            return true;
        case MotionEvent.ACTION_MOVE:
            // 터치 이동
            break;
        case MotionEvent.ACTION_UP:
            // 터치 종료
            break;
    }
    return super.onTouchEvent(event);
}
```

- `ACTION_DOWN`에서 `true`를 반환해야 이후의 이벤트(`MOVE`, `UP`)도 받을 수 있다.

**2. 터치 이벤트 전달 과정**

- 터치 이벤트는 액티비티 → 최상위 뷰 → 하위 뷰 순으로 `dispatchTouchEvent()`를 통해 전달된다.
- 각 뷰는 `onTouchEvent()`에서 이벤트를 처리할지 결정하며, 처리하지 않으면 상위 뷰로 전달된다.

**3. ViewGroup의 터치 이벤트 제어**

- ViewGroup(예: ScrollView, FrameLayout 등)은 `onInterceptTouchEvent()`를 통해 자식에게 이벤트를 넘길지 직접 처리할지 결정할 수 있다.
- `onInterceptTouchEvent()`에서 `true`를 반환하면 자식 뷰가 아닌 부모 뷰가 이벤트를 처리하게 된다.

**4. 멀티 터치 처리**

- 멀티 터치는 `MotionEvent.getPointerCount()`, `getActionIndex()`, `getActionMasked()`, `getPointerId()` 등으로 각 손가락의 상태와 좌표를 구분하여 처리할 수 있다.
- 멀티 터치의 경우 첫 번째 손가락은 `ACTION_DOWN/UP`, 두 번째 이상은 `ACTION_POINTER_DOWN/UP`으로 구분된다.

**5. OnTouchListener와 제스처 감지기**

- 뷰에 `setOnTouchListener()`를 등록해 터치 이벤트를 처리할 수 있고, `GestureDetector`를 활용하면 더 복잡한 제스처(더블탭, 플링 등)도 쉽게 처리할 수 있다.
