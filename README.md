# 머신러닝, 자연어 처리 기반 중고차 추천 플랫폼

### 1. 프로젝트 소개
#### 1.1. 배경 및 필요성
* 기존 LLM은 일반적인 질문에는 쉽게 답변하지만, 특정 분야의 전문적인 지식이 요구되는 질문에는 한계가 있다.
* 이를 보완하기 위해서는 해당 도메인에 특화된 추가 학습 또는 추가적인 정보 검색 및 연동 시스템이 필요하다.
* 중고차 매물 및 관련 정보들은 매우 방대하지만, 일반 소비자들이 원하는 정보를 찾기에는 힘든 경우가 많다.


#### 1.2. 목표 및 주요 내용
* 중고차 관련 지식이 부족한 일반 사용자도 쉽게 정보를 얻고, 적합한 매물을 추천받을 수 있는 챗봇을 제작한다.
* 간단하고 직관적인 인터페이스의 웹 애플리케이션 형태로 서비스를 제공하여 사용자가 쉽게 사용하도록 한다.

### 2. 상세설계
#### 2.1. RAG 모델
<div align="center">
    <img src="https://github.com/user-attachments/assets/961eee3d-045d-4b5b-bdf6-140e911c1490" alt="RAG 모델" />
</div>

#### 2.2. 챗봇 서비스
<div align="center">
    <img src="https://github.com/user-attachments/assets/6ae027a5-ee55-4864-b7c6-5ed91dadcca3" alt="챗봇 서비스" />
</div>

#### 2.3. 사용 기술
> 스택 별(backend, frontend, designer등) 사용한 기술 및 버전을 작성하세요.
> 
> ex) React.Js - React14, Node.js - v20.0.2

### 3. 소개 및 시연 영상
[![2024년 전기 졸업과제 34 도미노](http://img.youtube.com/vi/Cjc282zJxf8/0.jpg)](https://www.youtube.com/watch?v=Cjc282zJxf8)

### 4. 팀 소개
* 이은진 ltdmswls@pusan.ac.kr
  - 챗봇 웹페이지 UI 디자인 설계
  - 중고차 관련 외부 데이터 수집
  - RAG 시스템 설계 및 모델 구현
  - 프론트엔드 일부 기능 개발
  
* 최성빈 sb4759@naver.com
  - 데이터베이스 구조 설계
  - 백엔드 전체 시스템 개발
  - 프론트엔드 일부 기능 개발
  - 버그 수정 및 최종 테스트

* 최희웅 dhw1966@pusan.ac.kr
  - 중고차 매물 크롤링 및 전처리
  - 중고차 관련 외부 데이터 수집
  - Flask를 이용한 챗봇 API 구축
  - 프론트엔드 일부 기능 개발

