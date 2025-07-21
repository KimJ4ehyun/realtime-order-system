# 마이크로서비스 기반 실시간 주문 처리 및 알림 시스템 (Real-time Order Flow) 🚀

이 프로젝트는 쇼핑몰의 주문 시스템을 **마이크로서비스 아키텍처**로 구현하고, **실시간 알림 및 채팅 기능**을 추가하여 현대적인 분산 시스템의 구축 경험을 제공합니다. 특히 **Spring Cloud, Message Queue (Kafka/RabbitMQ), 그리고 WebSocket**의 세 가지 핵심 기술을 효과적으로 활용하는 것에 중점을 두었습니다.

---

## 💡 프로젝트 개요

본 프로젝트는 분산 시스템의 복잡성을 관리하고, 효율적인 확장성 및 유지보수성을 확보하기 위해 마이크로서비스 패턴을 적용했습니다. 사용자 주문 접수부터 재고 차감, 가상 결제 처리, 그리고 최종 사용자에게 실시간 알림을 제공하는 전 과정을 포괄합니다.

**이 프로젝트의 기본적인 기획과 기술 스택 구성은 Google의 인공지능 모델인 Gemini와의 상의를 통해 추천받아 구체화되었습니다.** 특히 마이크로서비스 아키텍처, 비동기 메시지 통신, 그리고 실시간 웹소켓 연동이라는 세 가지 핵심 기술을 유기적으로 결합하는 아이디어를 얻어 본 프로젝트를 시작하게 되었습니다.

---

## 🛠️ 주요 기술 스택

이 프로젝트는 다음과 같은 핵심 기술 스택을 활용합니다:

* **Spring Boot**: 각 마이크로서비스의 빠르고 효율적인 개발을 위한 프레임워크.
* **Spring Cloud**:
    * **Service Discovery (Eureka Server)**: 서비스 인스턴스를 동적으로 등록하고 찾아주는 역할을 합니다.
    * **Config Server**: 모든 마이크로서비스의 설정 정보를 중앙 집중식으로 관리합니다.
    * **Gateway (Spring Cloud Gateway)**: 클라이언트 요청의 단일 진입점으로, 라우팅, 인증/인가, 로깅 등 횡단 관심사를 처리합니다.
* **Message Queue (Kafka 또는 RabbitMQ)**:
    * 서비스 간의 **비동기 메시지 기반 통신**을 구현하여 시스템의 느슨한 결합을 달성하고 견고성을 높입니다. 주문 이벤트 발생 및 처리 결과 전파에 활용됩니다.
* **WebSocket**:
    * **실시간 양방향 통신**을 위한 기술로, 사용자에게 주문 상태 변화를 즉시 알리고 고객 지원을 위한 실시간 채팅 기능을 제공합니다.

---

## 📦 프로젝트 구성 요소

본 프로젝트는 다음과 같은 마이크로서비스들로 구성됩니다:

### 1. 주문 서비스 (Order Service)
* **역할**: 사용자로부터 주문을 접수하고, 주문 정보를 생성 및 관리합니다.
* **기술 활용**:
    * **Spring Cloud**: Eureka에 자신을 등록하고, Config Server에서 설정 정보를 가져옵니다.
    * **Message Queue**: 주문이 접수되면 "주문 생성됨" 이벤트를 메시지 큐로 발행하여 재고/결제 서비스가 비동기적으로 처리하도록 트리거합니다.

### 2. 재고/결제 서비스 (Inventory/Payment Service)
* **역할**: 주문 서비스에서 발행한 메시지를 구독하여 재고를 차감하고, 가상 결제 처리를 수행합니다. (실제 결제가 아닌 시뮬레이션입니다.)
* **기술 활용**:
    * **Spring Cloud**: Eureka에 자신을 등록하고, Config Server에서 설정 정보를 가져옵니다.
    * **Message Queue**: "주문 생성됨" 이벤트를 구독하여 처리하며, 재고 차감 및 결제 처리 결과("주문 처리 완료", "재고 부족" 등)를 다시 메시지 큐로 발행합니다.

### 3. 알림/채팅 서비스 (Notification/Chat Service)
* **역할**: 사용자에게 주문 처리 상태를 실시간으로 알리고, 고객 지원을 위한 실시간 채팅 기능을 제공합니다.
* **기술 활용**:
    * **Spring Cloud**: Eureka에 자신을 등록하고, Config Server에서 설정 정보를 가져옵니다.
    * **Message Queue**: 재고/결제 서비스에서 발행한 주문 처리 결과 메시지를 구독합니다.
    * **WebSocket**: 구독한 메시지를 기반으로 해당 사용자에게 실시간 알림을 보내고, 고객 지원을 위한 양방향 채팅 기능을 구현합니다.

### 4. API 게이트웨이 (API Gateway)
* **역할**: 모든 클라이언트 요청의 단일 진입점 역할을 수행합니다.
* **기술 활용**:
    * **Spring Cloud Gateway**: Eureka와 연동하여 동적으로 각 서비스의 위치를 찾아 요청을 라우팅합니다. 웹소켓 요청도 게이트웨이를 통해 라우팅하여 프론트엔드의 복잡성을 줄입니다.
    * 인증/인가, 로깅, 모니터링 등 **횡단 관심사(Cross-cutting concerns)**를 처리합니다.

### 5. 설정 서버 (Config Server)
* **역할**: 모든 마이크로서비스의 설정 정보를 중앙에서 관리합니다. Git 저장소에 설정 파일을 저장하고, 각 서비스가 시작될 때 이를 로드하도록 합니다.
* **기술 활용**: **Spring Cloud Config Server**를 사용하여 분산 환경에서의 설정 관리를 효율화합니다.

---

## 📚 이 프로젝트를 통해 얻을 수 있는 경험

이 프로젝트는 다음과 같은 핵심 역량과 지식을 제공합니다:

* **마이크로서비스 아키텍처 설계 및 구현**: 각 서비스의 역할을 명확히 분리하고, 상호 통신하는 방식을 이해합니다.
* **서비스 디스커버리 (Eureka)**: 동적으로 변화하는 서비스 인스턴스들을 관리하고 서로를 찾아 통신하는 방법을 배웁니다.
* **중앙 집중식 설정 관리 (Config Server)**: 여러 서비스의 설정을 효율적으로 관리하고 배포하는 방법을 익힙니다.
* **비동기 메시지 기반 통신 (Message Queue)**: 메시지 큐를 통한 느슨한 결합과 비동기 처리의 장점을 체감하며, 이벤트 기반 아키텍처를 경험합니다.
* **실시간 양방향 통신 (WebSocket)**: 웹소켓을 이용한 실시간 알림 및 채팅 기능 구현을 통해 실시간 애플리케이션 개발 능력을 향상시킵니다.
* **API 게이트웨이 패턴 (Spring Cloud Gateway)**: 클라이언트 요청을 효율적으로 라우팅하고, 시스템 전반의 보안 및 관리 기능을 강화하는 방법을 배웁니다.

---

## 🏗️ Git 저장소 구조 (모노레포)

이 프로젝트는 효율적인 개발 및 관리를 위해 모노레포(Monorepo) 구조를 채택했습니다. 모든 마이크로서비스 및 관련 설정 파일들은 단일 Git 저장소 내에 위치합니다.

```
real-time-order-system/
├── config-repo/             # Spring Cloud Config Server가 참조할 설정 파일들
│   └── order-service.yml
│   └── inventory-payment-service.yml
│   └── alert-service.yml
│   └── ...
├── config-server/           # Spring Cloud Config Server 애플리케이션
│   ├── src/main/java/...
│   └── build.gradle
├── eureka-server/           # Eureka Service Discovery Server 애플리케이션
│   ├── src/main/java/...
│   └── build.gradle
├── api-gateway/             # Spring Cloud Gateway 애플리케이션
│   ├── src/main/java/...
│   └── build.gradle
├── order-service/           # 주문 처리 마이크로서비스
│   ├── src/main/java/...
│   └── build.gradle
├── inventory-payment-service/ # 재고 차감 및 가상 결제 처리 마이크로서비스
│   ├── src/main/java/...
│   └── build.gradle
├── notification-chat-service/ # 실시간 알림 및 채팅 마이크로서비스
│   ├── src/main/java/...
│   └── build.gradle
└── docker-compose.yml       # 개발 환경 구성을 위한 Docker Compose 파일
```

---

## ⚙️ 시작하기

(이 섹션에는 프로젝트를 로컬에서 실행하기 위한 구체적인 단계가 포함됩니다. 예: Docker 설치, Git 클론, 각 서비스 빌드 및 실행 명령 등)

1.  **전제 조건**: Docker, Java 17+, Gradle (또는 Maven) 설치
2.  **프로젝트 클론**: `git clone [프로젝트_깃_주소]`
3.  **Docker Compose 실행**: `docker-compose up -d` (Kafka/RabbitMQ, Zookeeper 등 인프라 실행)
4.  **서비스 빌드 및 실행**: 각 서비스 폴더로 이동하여 `./gradlew bootRun` 또는 IDE를 통해 실행. (예: `cd config-server && ./gradlew bootRun`)