# 📱 DevView - 개발자 면접 준비 도우미

> 개발자 면접 준비의 새로운 관점을 제시하는 스마트 학습 앱

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 목차
- [소개](#-소개)
- [주요 기능](#-주요-기능)
- [스크린샷](#-스크린샷)
- [기술 스택](#-기술-스택)
- [사용법](#-사용법)
- [라이선스](#-라이선스)

## 🎯 소개

**DevView**는 개발자들의 기술 면접 준비를 위해 특별히 설계된 Android 앱입니다. 
130개 이상의 실무 중심 문제와 체계적인 학습 관리 시스템을 통해 효과적인 면접 준비를 지원합니다.

### ✨ 핵심 가치
- **실무 중심**: 실제 기술 면접에서 자주 나오는 문제들로 구성
- **개인화**: 사용자의 학습 패턴을 분석하여 맞춤형 추천 제공
- **체계적 관리**: 목표 설정부터 진도 추적까지 완전한 학습 생태계

## 🚀 주요 기능

### 📊 스마트 대시보드
- 개인화된 학습 현황 한눈에 보기
- 실시간 목표 달성률 추적
- AI 기반 맞춤형 퀴즈 추천

### 📝 4지선다 퀴즈 시스템
- **6개 카테고리**: 기술면접, 알고리즘, 데이터베이스, 네트워크, 운영체제, 종합
- **130+ 문제**: 실무 중심의 고품질 문제 은행
- **즉시 피드백**: 정답/오답 즉시 확인 및 상세 해설

### 📚 체계적 학습 관리
- **116개 면접 질문**: 실제 기출문제 기반 이론 학습
- **북마크 시스템**: 중요 문제 저장 및 관리
- **학습 기록 추적**: 읽은 문제 자동 기록

### 🎤 모의면접 연습
- **음성 녹음**: 실제 면접처럼 답변 녹음
- **재생 기능**: 본인 답변 재청취 및 개선점 파악
- **면접 기록**: 모든 연습 세션 자동 저장

### 👤 개인 맞춤 관리
- **상세 통계**: 카테고리별 성과 분석
- **목표 설정**: 일일/주간/월간 학습 목표 관리
- **스마트 알림**: 학습 리마인더 및 목표 달성 알림
- **데이터 백업**: JSON 기반 백업/복원 시스템

## 📱 스크린샷

| 홈 화면 | 퀴즈 | 학습 | 모의면접 | 마이페이지 |
|---------|------|------|----------|------------|
| ![Home](screenshots/home.png) | ![Quiz](screenshots/quiz.png) | ![Study](screenshots/study.png) | ![Interview](screenshots/interview.png) | ![Profile](screenshots/profile.png) |

## 🛠️ 기술 스택

### Frontend
- **언어**: Java
- **프레임워크**: Android SDK (API 21+)
- **UI**: Material Design Components 3.0
- **아키텍처**: Fragment 기반 Single Activity

### 데이터 관리
- **로컬 저장소**: SharedPreferences
- **백업 시스템**: JSON 기반 데이터 백업/복원
- **미디어**: MediaRecorder API (음성 녹음)

### 주요 라이브러리
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.recyclerview:recyclerview:1.3.0'
implementation 'androidx.cardview:cardview:1.0.0'

## 📖 사용법

### 1. 첫 시작
- 앱 설치 후 프로필 설정
- 학습 목표 설정 (일일/주간)
- 알림 시간 설정

### 2. 학습 진행
- **홈 탭**: 빠른 시작으로 추천 퀴즈 시작
- **학습 탭**: 이론 학습으로 기초 다지기
- **퀴즈 탭**: 4지선다 문제로 실력 점검
- **모의면접 탭**: 실전 연습으로 마무리

### 3. 진도 관리
- **마이페이지**: 상세 통계 및 성과 분석
- **목표 설정**: 개인 맞춤 학습 계획 수립
- **알림 활용**: 꾸준한 학습 습관 형성

## 🤝 기여하기

DevView 프로젝트에 기여해주셔서 감사합니다! 

## 🔮 로드맵

### v1.1 (예정)
- [ ] AI 기반 답변 분석 (OpenAI API 연동)
- [ ] 클라우드 동기화 (Firebase 연동)
- [ ] 다크모드 완전 지원

### v1.2 (예정)
- [ ] 음성 인식 기능 (STT)
- [ ] 실시간 면접 코칭
- [ ] 소셜 기능 (학습 그룹)

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 👨‍💻 개발팀

- **DevView Team** - *Initial work* - [GitHub](https://github.com/sam3319)

## 📞 문의

- **이메일**: chol294240@gmail.com
- **이슈**: [GitHub Issues](https://github.com/sam3319/FinalProject/issues)

---

⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!

**DevView와 함께 성공적인 개발자 면접을 준비하세요!** 🚀
