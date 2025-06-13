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
- [설치 방법](#-설치-방법)
- [사용법](#-사용법)
- [기여하기](#-기여하기)
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
