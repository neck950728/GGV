<br>

---

<div align="center">
   <img src="https://github.com/neck950728/GGV/assets/151998896/099efcb3-79f9-4c0a-a19f-28f7bd0a5635" width="18%">
   <h6><i>본 사이트는 상업적 목적이 아닌 개인 포트폴리오 용도로 제작되었습니다.</i></h6>
</div>

---

<br><br>

><p><strong>분류</strong> - 개인 프로젝트</p>
><p><strong>제목</strong> - GGV</p>
><p><strong>주제</strong> - 영화 예매 사이트</p>
><p><strong>기획 및 제작</strong> - 김민진</p>
><p><strong>제작 시기</strong> - 2018.06</p>

<br><br>

## :open_file_folder: 목차
<ul>
   <li><a href="#project-introduction">프로젝트 소개</a></li>
   <li><a href="#development-configuration">개발 환경</a></li>
   <li><a href="#screen-configuration">화면 구성</a></li>
   <li id="function"><a href="#function">기능</a></li>
   <details>
      <summary>접기/펼치기</summary>
      
      1. 접속
         1.1. 메인
         1.2. 회원가입
         1.3. 로그인
      2. 영화
         2.1. 무비차트
         2.2. 영화 상세
         2.3. 트레일러
         2.4. 예매
      3. 컬처
         3.1. 장바구니
         3.2. 구매
      4. 마이페이지
         4.1. 정보 수정
         4.2. 위시리스트
         4.3. 내가 본 영화
         4.4. 예매 내역
         4.5. 구매 내역
      5. 고객센터
         5.1. 메인
         5.2. 1:1 문의
      6. 관리자
         6.1. 영화 관리
   </details>
</ul>

<br><br>

## 🚀 <a id="project-introduction">프로젝트 시작 계기</a>
:clapper: ```'GGV'```는 국비 교육 과정을 이수하고 난 후에도,<br>
아직 많은 부분에서 미숙하다고 느껴 시작하게 된 프로젝트입니다.<br>
그중에서도 영화 예매 사이트가 웹 애플리케이션의 전반적인 기능들을 모두 포함하고 있어,<br>
학습 차원에서 좋을 것 같아 CGV 사이트를 모티브로 한 프로젝트를 진행하게 되었습니다.<br>

<br><br>

## :gear: <a id="development-configuration">개발 환경</a>
<img src="https://img.shields.io/badge/Framework-121011?style=for-the-badge"> <img src ="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis-004088?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMCIgd2lkdGg9IjEwOC4wMDAwMDBwdCIgaGVpZ2h0PSI4OC4wMDAwMDBwdCIgdmlld0JveD0iMCAwIDEwOC4wMDAwMDAgODguMDAwMDAwIiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWlkWU1pZCBtZWV0Ij4KCjxnIHRyYW5zZm9ybT0idHJhbnNsYXRlKDAuMDAwMDAwLDg4LjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIgZmlsbD0id2hpdGUiIHN0cm9rZT0ibm9uZSI+CjxwYXRoIGQ9Ik0zNTQgNzk1IGMtOSAtMjMgMTUgLTQ1IDQ3IC00NSAxNSAwIDMxIC01IDM1IC0xMSA1IC05IC0xIC0xMCAtMjEgLTUgLTE3IDQgLTQ5IDAgLTgzIC0xMCBsLTU1IC0xOCAtMjMgMjkgYy0yOSAzNyAtNTIgMjQgLTMzIC0xOCAxNiAtMzQgMzIgLTM5IDczIC0yMiA0NCAxOSAxMTAgMTkgMTI1IDIgNiAtOCAxNCAtMjkgMTggLTQ3IGw2IC0zMyAtNDkgNiBjLTI3IDQgLTYxIDEwIC03NSAxMyAtMjUgNiAtMjYgNCAtMjUgLTMzIGwxIC0zOCAtNTcgLTMgYy0zMiAtMiAtNTggLTcgLTU4IC0xMyAwIC0yMiAyOSAtNzggNTkgLTExMSAxNyAtMjAgMzEgLTQzIDMxIC01MSAwIC0yMCAtNDggLTU2IC0xMzAgLTk3IGwtNjUgLTMzIDQ4IC00MyBjNjEgLTU1IDE1MyAtMTAwIDI0MiAtMTE5IDg3IC0xOCAxMzQgLTE4IDIwNiAtMSAxNDAgMzIgMjI1IDEyMiAyODUgMzAwIDEzIDM4IDMyIDc1IDQxIDgyIDE0IDEwIDE1IDEzIDQgMTQgLTExIDAgLTEwIDMgMyAxMyAxNiAxMiAxNyAxNSAzIDIwIC04IDMgLTMwIDM1IC00NyA3MiAtNDIgODggLTg4IDEyMCAtMTc0IDEyMCAtNDQgMCAtNzQgLTcgLTEwOCAtMjQgLTU3IC0yOSAtNzggLTIzIC03OCAyMiAwIDQzIC0xNSA1NyAtNjIgNTcgLTI3IDAgLTQwIDUgLTQ4IDIwIC0xMyAyNCAtMjggMjYgLTM2IDV6IG00NDYgLTIxNSBjMTIgLTggMTEgLTEwIC03IC0xMCAtNDIgMCAtMzIgLTU5IDEwIC02MSAxMiAwIDE2IC0zIDkgLTYgLTIzIC05IC02MyA3IC03MSAzMCAtMTYgNDMgMjAgNzIgNTkgNDd6IG0zMCAtMzAgYzAgLTUgLTQgLTEwIC05IC0xMCAtNiAwIC0xMyA1IC0xNiAxMCAtMyA2IDEgMTAgOSAxMCA5IDAgMTYgLTQgMTYgLTEweiIvPgo8L2c+Cjwvc3ZnPg==">

<img src="https://img.shields.io/badge/IDE-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/Spring Tool Suite-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

<img src="https://img.shields.io/badge/Tool-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/SQL Developer-818D94?style=for-the-badge&logo=data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMzM2LjAwMDAwMHB0IiBoZWlnaHQ9IjQwOS4wMDAwMDBwdCIgdmlld0JveD0iMCAwIDMzNi4wMDAwMDAgNDA5LjAwMDAwMCIKIHByZXNlcnZlQXNwZWN0UmF0aW89InhNaWRZTWlkIG1lZXQiPgoKPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsNDA5LjAwMDAwMCkgc2NhbGUoMC4xMDAwMDAsLTAuMTAwMDAwKSIKZmlsbD0iI0ZGRkZGRiIgc3Ryb2tlPSJub25lIj4KPHBhdGggZD0iTTEzMjUgMzkzOSBjLTEyMCAtMTAgLTI3MCAtMjggLTMyNSAtMzkgLTI1IC01IC03NCAtMTQgLTExMCAtMjEKLTIzOCAtNDMgLTQ4MyAtMTM4IC01OTkgLTIyOSAtMTEzIC05MCAtMTQzIC0xOTYgLTgxIC0yOTMgNjAgLTk0IDIyOSAtMTkyCjQ1NSAtMjYxIDEzNyAtNDIgMzI5IC03OCA1NjUgLTEwNyAxNDAgLTE4IDcyMCAtMTggODU1IC0xIDMzMSA0MiA1MTYgODEgNzAwCjE1MSA2MCAyMyAxOTcgOTYgMjQwIDEyOSAxODUgMTQwIDE0NyAzMTkgLTk1IDQ0NiAtMjkzIDE1NCAtNzQ4IDIzNyAtMTI4NQoyMzQgLTEyNiAwIC0yNzAgLTQgLTMyMCAtOXoiLz4KPHBhdGggZD0iTTE1MiAzMjMxIGMtOCAtMjUgLTEyIC0xMzcgLTEyIC0zMjYgbDAgLTI4NyAzMiAtNjcgYzg1IC0xNzggMzM5Ci0zMzAgNzA5IC00MjQgMTIyIC0zMSAxNjYgLTQwIDMzOSAtNjcgMzAgLTUgMTQ0IC0xNCAyNTMgLTIxIDQxNCAtMjYgODQ4IDMxCjEyMDAgMTU4IDg4IDMxIDI0NSAxMTMgMzA5IDE2MCA2OCA0OSAxNDUgMTM5IDE3NCAyMDEgMjQgNTIgMjQgNTYgMjQgMzQ5IDAKMjg0IC03IDM2MyAtMzIgMzYzIC00IDAgLTE2IC0xNyAtMjcgLTM4IC04MSAtMTYwIC00MjIgLTMwNSAtODU2IC0zNjYgLTI1NAotMzUgLTMxMCAtMzkgLTYwMCAtMzkgLTM0NSAxIC00OTcgMTQgLTc3NSA2OSAtMzU5IDcyIC02MDQgMTg5IC02ODYgMzMwIC0xNAoyNCAtMjkgNDQgLTMzIDQ0IC00IDAgLTEzIC0xNyAtMTkgLTM5eiIvPgo8cGF0aCBkPSJNMTY0IDIzMzMgYy0zIC0xMCAtMTAgLTMwIC0xNSAtNDQgLTYgLTE0IC05IC0xNTIgLTcgLTMyNSAzIC0zNDQgNQotMzQ5IDEwMiAtNDUyIDMwMyAtMzIxIDExMTUgLTQ4NiAxODgxIC0zODEgOTQgMTMgMjYzIDQ1IDMyMCA2MSAyMiA2IDU0IDE0CjcwIDE5IDMxNCA4OCA1NTkgMjQ0IDYzNSA0MDUgbDMwIDYyIDAgMjkyIGMwIDI5NCAtNyAzNjYgLTM3IDM3NyAtNyAzIC0yMyAtOAotMzYgLTIzIC04NSAtMTEwIC0yMjEgLTE5OSAtNDMyIC0yODQgLTE2NSAtNjYgLTQwMiAtMTE5IC02NzEgLTE1MCAtMTE2IC0xMwotNTgwIC0xMyAtNjgxIDAgLTI1NiAzMyAtMzI0IDQ1IC00NTMgNzkgLTQxIDExIC0xMDkgMzIgLTE1MCA0NiAtNDEgMTUgLTg0CjI5IC05NSAzMiAtMzQgMTEgLTE4OCA5MCAtMjQ0IDEyNiAtNTMgMzQgLTE4MSAxNTMgLTE4MSAxNjkgMCAxNiAtMzEgOCAtMzYKLTl6Ii8+CjxwYXRoIGQ9Ik0xNzMgMTM4OCBjLTI4IC0zNiAtMzMgLTg5IC0zMyAtMzY3IDAgLTM0MCA0IC0zNTcgMTA2IC00NzAgMTIwCi0xMzMgMzgyIC0yNjAgNjg0IC0zMzAgMTk5IC00NyA1MDggLTgxIDczMCAtODEgMTI2IDAgNDAwIDIxIDUyMCA0MCA0OTYgNzgKODc0IDI2NiA5NzQgNDgzIGwyNiA1OCAwIDI5OCBjMCAyOTMgLTUgMzQzIC0zNSAzNzMgLTQgNCAtMzQgLTIzIC02OCAtNTkKLTEzOSAtMTQ5IC0zNTUgLTI1NiAtNjY3IC0zMzEgLTEyMCAtMjggLTE2MSAtMzYgLTM1MCAtNjQgLTExNiAtMTggLTY3MiAtMTcKLTgwMCAwIC0xNjAgMjIgLTIxMSAzMSAtMzYzIDY3IC0yODggNjggLTUzMSAxOTIgLTY1OSAzMzUgLTQyIDQ4IC01NyA1OCAtNjUKNDh6Ii8+CjwvZz4KPC9zdmc+"> <img src="https://img.shields.io/badge/MySQL Workbench-08668E?style=for-the-badge&logo=mysql&logoColor=white">

<img src="https://img.shields.io/badge/Language-121011?style=for-the-badge"> <img src ="https://img.shields.io/badge/HTML-E34F26?&style=for-the-badge&logo=html5&logoColor=white"> <img src ="https://img.shields.io/badge/CSS-1572B6?&style=for-the-badge&logo=css3&logoColor=white"> <img src ="https://img.shields.io/badge/JavaScriipt-F7DF1E?&style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/Java-FF8224?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA1MCA1MCIgd2lkdGg9IjUwcHgiIGhlaWdodD0iNTBweCI+DQogIDxwYXRoIGQ9Ik0gMjguMTg3NSAwIEMgMzAuOTM3NSA2LjM2MzI4MSAxOC4zMjgxMjUgMTAuMjkyOTY5IDE3LjE1NjI1IDE1LjU5Mzc1IEMgMTYuMDgyMDMxIDIwLjQ2NDg0NCAyNC42NDg0MzggMjYuMTI1IDI0LjY1NjI1IDI2LjEyNSBDIDIzLjM1NTQ2OSAyNC4xMDkzNzUgMjIuMzk4NDM4IDIyLjQ0OTIxOSAyMS4wOTM3NSAxOS4zMTI1IEMgMTguODg2NzE5IDE0LjAwNzgxMyAzNC41MzUxNTYgOS4yMDcwMzEgMjguMTg3NSAwIFogTSAzNi41NjI1IDguODEyNSBDIDM2LjU2MjUgOC44MTI1IDI1LjUgOS41MjM0MzggMjQuOTM3NSAxNi41OTM3NSBDIDI0LjY4NzUgMTkuNzQyMTg4IDI3Ljg0NzY1NiAyMS4zOTg0MzggMjcuOTM3NSAyMy42ODc1IEMgMjguMDExNzE5IDI1LjU1ODU5NCAyNi4wNjI1IDI3LjEyNSAyNi4wNjI1IDI3LjEyNSBDIDI2LjA2MjUgMjcuMTI1IDI5LjYwOTM3NSAyNi40NDkyMTkgMzAuNzE4NzUgMjMuNTkzNzUgQyAzMS45NDkyMTkgMjAuNDI1NzgxIDI4LjMyMDMxMyAxOC4yODUxNTYgMjguNjg3NSAxNS43NSBDIDI5LjAzOTA2MyAxMy4zMjQyMTkgMzYuNTYyNSA4LjgxMjUgMzYuNTYyNSA4LjgxMjUgWiBNIDE5LjE4NzUgMjUuMTU2MjUgQyAxOS4xODc1IDI1LjE1NjI1IDkuMDYyNSAyNS4wMTE3MTkgOS4wNjI1IDI3Ljg3NSBDIDkuMDYyNSAzMC44NjcxODggMjIuMzE2NDA2IDMxLjA4OTg0NCAzMS43ODEyNSAyOS4yNSBDIDMxLjc4MTI1IDI5LjI1IDM0LjI5Njg3NSAyNy41MTk1MzEgMzQuOTY4NzUgMjYuODc1IEMgMjguNzY1NjI1IDI4LjE0MDYyNSAxNC42MjUgMjguMjgxMjUgMTQuNjI1IDI3LjE4NzUgQyAxNC42MjUgMjYuMTc5Njg4IDE5LjE4NzUgMjUuMTU2MjUgMTkuMTg3NSAyNS4xNTYyNSBaIE0gMzguNjU2MjUgMjUuMTU2MjUgQyAzNy42NjQwNjMgMjUuMjM0Mzc1IDM2LjU5Mzc1IDI1LjYxNzE4OCAzNS42MjUgMjYuMzEyNSBDIDM3LjkwNjI1IDI1LjgyMDMxMyAzOS44NDM3NSAyNy4yMzQzNzUgMzkuODQzNzUgMjguODQzNzUgQyAzOS44NDM3NSAzMi40Njg3NSAzNC41OTM3NSAzNS44NzUgMzQuNTkzNzUgMzUuODc1IEMgMzQuNTkzNzUgMzUuODc1IDQyLjcxODc1IDM0Ljk1MzEyNSA0Mi43MTg3NSAyOSBDIDQyLjcxODc1IDI2LjI5Njg3NSA0MC44Mzk4NDQgMjQuOTg0Mzc1IDM4LjY1NjI1IDI1LjE1NjI1IFogTSAxNi43NSAzMC43MTg3NSBDIDE1LjE5NTMxMyAzMC43MTg3NSAxMi44NzUgMzEuOTM3NSAxMi44NzUgMzMuMDkzNzUgQyAxMi44NzUgMzUuNDE3OTY5IDI0LjU2MjUgMzcuMjA3MDMxIDMzLjIxODc1IDMzLjgxMjUgTCAzMC4yMTg3NSAzMS45Njg3NSBDIDI0LjM1MTU2MyAzMy44NDc2NTYgMTMuNTQ2ODc1IDMzLjIzNDM3NSAxNi43NSAzMC43MTg3NSBaIE0gMTguMTg3NSAzNS45Mzc1IEMgMTYuMDU4NTk0IDM1LjkzNzUgMTQuNjU2MjUgMzcuMjIyNjU2IDE0LjY1NjI1IDM4LjE4NzUgQyAxNC42NTYyNSA0MS4xNzE4NzUgMjcuMzcxMDk0IDQxLjQ3MjY1NiAzMi40MDYyNSAzOC40Mzc1IEwgMjkuMjE4NzUgMzYuNDA2MjUgQyAyNS40NTcwMzEgMzcuOTk2MDk0IDE2LjAxNTYyNSAzOC4yMzgyODEgMTguMTg3NSAzNS45Mzc1IFogTSAxMS4wOTM3NSAzOC42MjUgQyA3LjYyNSAzOC41NTQ2ODggNS4zNzUgNDAuMTEzMjgxIDUuMzc1IDQxLjQwNjI1IEMgNS4zNzUgNDguMjgxMjUgNDAuODc1IDQ3Ljk2NDg0NCA0MC44NzUgNDAuOTM3NSBDIDQwLjg3NSAzOS43Njk1MzEgMzkuNTI3MzQ0IDM5LjIwMzEyNSAzOS4wMzEyNSAzOC45Mzc1IEMgNDEuOTMzNTk0IDQ1LjY1NjI1IDkuOTY4NzUgNDUuMTIxMDk0IDkuOTY4NzUgNDEuMTU2MjUgQyA5Ljk2ODc1IDQwLjI1MzkwNiAxMi4zMjAzMTMgMzkuMzkwNjI1IDE0LjUgMzkuODEyNSBMIDEyLjY1NjI1IDM4Ljc1IEMgMTIuMTEzMjgxIDM4LjY2Nzk2OSAxMS41ODk4NDQgMzguNjM2NzE5IDExLjA5Mzc1IDM4LjYyNSBaIE0gNDQuNjI1IDQzLjI1IEMgMzkuMjI2NTYzIDQ4LjM2NzE4OCAyNS41NDY4NzUgNTAuMjIyNjU2IDExLjc4MTI1IDQ3LjA2MjUgQyAyNS41NDI5NjkgNTIuNjk1MzEzIDQ0LjU1ODU5NCA0OS41MzUxNTYgNDQuNjI1IDQzLjI1IFoiIGZpbGw9IiNGRkZGRkYiLz4NCjwvc3ZnPg==">

<img src="https://img.shields.io/badge/DB-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/Oracle-FF0000?style=for-the-badge&logo=oracle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-08668E?style=for-the-badge&logo=mysql&logoColor=white">

<img src="https://img.shields.io/badge/Server-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black">

<img src="https://img.shields.io/badge/OS-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows10&logoColor=white">

<img src="https://img.shields.io/badge/Hosting-121011?style=for-the-badge"> <img src="https://img.shields.io/badge/cafe24-0265F4?style=for-the-badge">

|:hammer: Library & API|
|:---:|
```jQuery```, ```Jackson Databind```, ```JSON Simple```, ```Gson``` <br> ```Commons FileUpload```, ```Summernote```, ```Google Charts```, ```Selenium``` <br> ```JXLS```, ```Javax Mail```, ```CoolSMS```, ```Lucy XSS Servlet Filter```

<br><br>

## :tv: <a id="screen-configuration">화면 구성</a>
|메인|회원가입|
|---|---|
|<img src="https://github.com/neck950728/GGV/assets/151998896/4fbebb3d-4e66-4f5c-87b4-4bbc68a92cea" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/83619f91-4871-44b4-b471-06be51be2ffa" width="387.5">|

|로그인|무비차트|
|---|---|
|<img src="https://github.com/neck950728/GGV/assets/151998896/06fe8b19-f529-4ab1-a6ca-dbb16e9b9524" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/7090cd42-7afc-403a-bd7f-bd615fbdc52b" width="387.5">|

|영화 상세|트레일러|
|---|---|
|<img src="https://github.com/neck950728/GGV/assets/151998896/95414cbf-a3f9-4b91-a23d-40b39e55b5fc" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/621ed394-5422-43ed-a140-3046045f22f6" width="387.5">|

|예매|컬처|
|---|---|
|<img src="https://github.com/neck950728/GGV/assets/151998896/2f6cdf0c-a72a-46d2-8829-dfb72e1a9b17" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/a3c1e0d1-f0d3-439f-a2b6-8af77741c953" width="387.5">|

|마이페이지|고객센터|
|---|---|
<img src="https://github.com/neck950728/GGV/assets/151998896/7e24f234-93df-407c-a4df-52c74403998c" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/9a60c248-9588-430c-a0ec-075679ab4393" width="387.5">|

|영화 등록/수정|영화 등록(Excel)|
|---|---|
<img src="https://github.com/neck950728/GGV/assets/151998896/a00f94a8-3328-4f3c-a0ca-53e7f9720b60" width="387.5">|<img src="https://github.com/neck950728/GGV/assets/151998896/7854d842-56b3-4b65-91c8-fcd64e1e76aa" width="387.5">|
