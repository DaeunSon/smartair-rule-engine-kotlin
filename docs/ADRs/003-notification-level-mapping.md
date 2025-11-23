# ADR 003: Notification 레벨 매핑 전략

## 상태

승인됨

## 컨텍스트

이상 패턴이 감지되면 사용자에게 알림을 제공해야 합니다. 이상의 심각도에 따라 다른 레벨의 알림을 제공해야 합니다.

현재 시스템에는 3가지 이상 타입이 있습니다:
- `THRESHOLD_EXCEEDED`: 임계치 초과
- `RAPID_RISE`: 급상승
- `SENSOR_ERROR`: 센서 오류

## 결정

이상 타입에 따라 다음과 같이 알림 레벨을 매핑합니다:

- `THRESHOLD_EXCEEDED` → `WARNING`
- `RAPID_RISE` → `DANGER`
- `SENSOR_ERROR` → `INFO`

`NotificationLevelMapper` 객체를 통해 매핑 로직을 중앙화했습니다.

## 고려한 대안

### 대안 1: NotificationFactory에서 직접 매핑

```kotlin
object NotificationFactory {
    fun from(report: AnomalyReport): Notification {
        val level = when (report.type) {
            THRESHOLD_EXCEEDED -> WARNING
            RAPID_RISE -> DANGER
            SENSOR_ERROR -> INFO
        }
        // ...
    }
}
```

**장점**:
- 한 곳에 모든 로직이 있음
- 간단함

**단점**:
- NotificationFactory의 책임이 증가
- 매핑 로직 변경 시 NotificationFactory 수정 필요
- 테스트가 어려움

### 대안 2: 현재 선택 (별도 Mapper 객체)

**장점**:
- 단일 책임 원칙 준수
- 매핑 로직이 독립적으로 테스트 가능
- 매핑 전략 변경이 용이
- 재사용 가능

**단점**:
- 클래스 수 증가 (하지만 작은 증가)

### 대안 3: AnomalyType에 레벨 포함

```kotlin
enum class AnomalyType(val level: NotificationLevel) {
    THRESHOLD_EXCEEDED(WARNING),
    RAPID_RISE(DANGER),
    SENSOR_ERROR(INFO)
}
```

**장점**:
- 타입과 레벨이 함께 정의됨
- 매핑 로직 불필요

**단점**:
- 도메인 모델에 알림 도메인 의존성 추가
- 순환 의존성 가능성
- 도메인 분리 원칙 위반

## 결과

- `NotificationLevelMapper`를 통해 명확한 매핑 전략 제공
- 매핑 로직이 독립적으로 테스트 가능
- 향후 매핑 전략 변경 시 Mapper만 수정하면 됨
- 도메인 간 의존성이 명확함

## 참고

- Strategy 패턴 적용
- Single Responsibility Principle 준수

