# 모바일 앱 프로그래밍 여섯 번째 수업 내용

### 고급 위젯과 이벤트 처리하기

# 📱 Android Studio의 이벤트 처리(Event Handling)

안드로이드 앱에서 **이벤트 처리**는 사용자와 상호작용을 다루는 핵심 기능이다..  
버튼 클릭, 텍스트 입력, 체크박스 선택 등의 사용자 행동에 반응하는 코드를 작성해야 한다..

---

## ✅ 이벤트 처리란?

이벤트(event)란 사용자 또는 시스템에 의해 발생하는 사건이다. 
Android에서 이벤트는 **View(버튼, 체크박스 등)** 와 **리스너(listener)** 를 통해 처리된다..

---

## 🧩 주요 이벤트 리스너 종류

| 리스너 인터페이스          | 설명                              |
|---------------------------|----------------------------------|
| `OnClickListener`         | 클릭 이벤트 처리 (버튼 등)         |
| `OnTouchListener`         | 터치(눌렀을 때) 이벤트 처리        |
| `OnLongClickListener`     | 길게 누르기 이벤트 처리           |
| `OnCheckedChangeListener` | 체크박스/라디오버튼 선택 변화 처리 |
| `TextWatcher`             | 텍스트 입력 변화 감지             |

---

## ✍️ 이벤트 처리 방법

### 1. **익명 클래스 사용 (추천)**

```java
Button btn = findViewById(R.id.myButton);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 이벤트 처리 코드
    }
});

