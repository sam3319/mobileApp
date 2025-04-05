# 모바일 앱 프로그래밍 네 번째 수업 내용

### 레이아웃(Linear, Table, Relative, Grid, Constrain)


## 🧩 Android Layout 구성 (activity_main.xml)

이 앱은 XML을 활용하여 **직관적인 계산기 UI**를 구성합니다.  
레이아웃은 `LinearLayout`과 `GridLayout`을 조합하여 버튼을 정렬합니다.

---

### 📄 주요 레이아웃 구조

```xml
<LinearLayout
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- 결과를 보여주는 TextView -->
    <TextView
        android:id="@+id/tvResult"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:gravity="end|center_vertical"
        android:text="0"
        android:textSize="36sp" />

    <!-- 버튼들을 배치한 GridLayout -->
    <GridLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:columnCount="4"
        android:rowCount="5">

        <!-- 예시 버튼 -->
        <Button android:id="@+id/btnAC" android:text="AC" />
        <Button android:id="@+id/btnDiv" android:text="/" />
        <Button android:id="@+id/btnMul" android:text="*" />
        <Button android:id="@+id/btnSub" android:text="-" />
        <!-- ... 숫자 및 기타 버튼들 ... -->
    </GridLayout>

</LinearLayout>
```

###🧱 버튼 구성
버튼은 다음과 같은 기능을 포함합니다:

숫자 버튼: 0 ~ 9

연산자 버튼: +, -, *, /

기능 버튼: =, AC, .

결과 출력: TextView로 결과 표시


###📐 정렬 방식
전체 레이아웃: LinearLayout (Vertical)

버튼 배치: GridLayout (4열 x 5행)

결과 출력: 상단 TextView, 우측 정렬

yaml
복사
편집
