## 트리플 과제 2 (트리플여행자 클럽 마일리지 서비스)

![Generic badge](https://img.shields.io/badge/java-11-black)
![Generic badge](https://img.shields.io/badge/spring%20boot-2.5.4-brightgreen)
![Generic badge](https://img.shields.io/badge/mysql-5.7-blue)

### API 소개
- GET /point/{userId}
  - 유저 아이디가 갖고 있는 포인트와 포인트 적립/차감 내역을 반환
  - userId는 UUID 형태를 띄고 있음
- POST /point
  - 리뷰 이벤트가 발생 시, 리뷰를 저장하고 액션에 따라 포인트를 적립/차감한다.
  - Content-Type은 `application/json`이다.
  - 과제에서 안내한 json 형태를 띄고 있음
- GET /util/user
  - 유저 아이디를 보여주는 API
  - 유틸성으로 테스트 시 유저 아이디를 편하게 쓰기 위해 만들어 놓음
  - 앱 실행 시, 미리 2명의 유저를 생성하도록 함.

### 사용 방법
- Database 세팅(mysql:5.7)
  - 이 과제에선 mysql docker image를 가지고 했습니다.
  - 다음 과정을 통해 mysql 이미지를 받아 실행 후 mysql 컨테이너에 진입합니다.
    ```bash
    docker pull mysql:5.7
    docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:5.7
    docker exec -it mysql bash
    ```
  - `mysql -uroot -p`를 입력하고 비밀번호로 `root`를 입력합니다.
  - mysql 안에서 `clubmileages` 데이터베이스를 생성합니다.
    ```sql
    create database clubmileages default character set utf8 collate utf8_general_ci;
    ```
    - utf8을 세팅하는 이유는 한글 데이터를 insert시, 인코딩 에러를 방지합니다.
- 애플리케이션 실행
  - `./gradlew bootRun` 명령어를 통해 실행

### 에러 핸들링
- schema가 이미 존재한다고 합니다.
  - 이미 스키마가 존재하는데, 현재 `classpath:/db/schema.sql`을 통해 스키마를 다시 초기화하고 있는 것입니다.
  - `application.yml` 내에서 `sql.init.mode`를 `never`로 바꿔줍니다.