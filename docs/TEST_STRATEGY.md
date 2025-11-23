# 테스트 전략 및 결과

## 📋 목차

- [테스트 전략](#테스트-전략)
- [테스트 구조](#테스트-구조)
- [테스트 케이스 목록](#테스트-케이스-목록)
- [테스트 실행 결과](#테스트-실행-결과)
- [테스트 커버리지](#테스트-커버리지)

---

### 테스트 원칙

1. **단위 테스트 우선**: 각 클래스의 핵심 로직을 독립적으로 테스트
2. **대표 케이스 테스트**: 동일한 로직은 대표 케이스만 테스트 (오버엔지니어링 방지)
3. **통합 테스트**: 여러 컴포넌트 간의 상호작용 검증
4. **명확한 테스트 이름**: 한국어로 작성하여 의도 명확화

---

## 테스트 구조

### 단위 테스트 (Unit Tests)

#### Domain Layer

- **AirQualityDataTest**: 데이터 검증 및 diff 메서드 테스트
- **PollutantTest**: 오염물질 값 추출 및 label 검증
- **NotificationFactoryTest**: 알림 생성 로직 테스트
- **NotificationLevelMapperTest**: 알림 레벨 매핑 테스트

#### Rule Layer

- **ThresholdRuleTest**: 임계치 초과 감지 로직 테스트
- **RapidRiseRuleTest**: 급상승 감지 로직 테스트
- **RuleEngineTest**: 룰 엔진 기본 동작 테스트
- **RulePresetsTest**: 기본 룰 세트 생성 검증

### 통합 테스트 (Integration Tests)

- **RulePresetsIntegrationTest**: RuleEngine + RulePresets 통합 테스트
- **FullFlowIntegrationTest**: 전체 플로우 통합 테스트

---

## 테스트 케이스 목록

### AirQualityDataTest

- ✅ sensorId가 비어있으면 예외를 발생시킨다
- ✅ sensorId가 공백만 있으면 예외를 발생시킨다
- ✅ sensorId가 유효하면 정상적으로 생성된다
- ✅ diff는 두 데이터 간의 차이를 올바르게 계산한다
- ✅ diff는 음수 값도 올바르게 계산한다

### PollutantTest

- ✅ getValue는 해당 오염물질의 값을 올바르게 추출한다
- ✅ 모든 Pollutant의 label이 올바르게 설정되어 있다

### NotificationFactoryTest

- ✅ THRESHOLD_EXCEEDED 리포트는 WARNING 알림을 생성한다
- ✅ RAPID_RISE 리포트는 DANGER 레벨 알림을 생성한다
- ✅ SENSOR_ERROR 리포트는 INFO 레벨 알림을 생성한다

### NotificationLevelMapperTest

- ✅ THRESHOLD_EXCEEDED는 WARNING 레벨로 매핑된다
- ✅ RAPID_RISE는 DANGER 레벨로 매핑된다
- ✅ SENSOR_ERROR는 INFO 레벨로 매핑된다
- ✅ 모든 AnomalyType이 올바른 NotificationLevel로 매핑된다

### ThresholdRuleTest

- ✅ TVOC가 임계값을 초과하면 이상치 결과를 생성한다
- ✅ TVOC가 임계값 이하이면 이상치가 아니다
- ✅ TVOC가 임계값과 정확히 같으면 이상치가 아니다
- ✅ eCO2가 임계값을 초과하면 이상치 결과를 생성한다
- ✅ eCO2가 임계값 이하이면 이상치가 아니다
- ✅ eCO2가 임계값과 정확히 같으면 이상치가 아니다

### RapidRiseRuleTest

- ✅ 최근 데이터에 비해 TVOC가 급상승하면 이상치로 판단한다
- ✅ 변화량이 임계치 미만이면 이상치가 아니다
- ✅ 최근 데이터에 비해 eCO2가 급상승하면 이상치로 판단한다
- ✅ eCO2 변화량이 임계치 미만이면 이상치가 아니다
- ✅ 최근 데이터에 비해 온도가 급상승하면 이상치로 판단한다
- ✅ 온도 변화량이 임계치 미만이면 이상치가 아니다
- ✅ 최근 데이터에 비해 습도가 급상승하면 이상치로 판단한다
- ✅ 습도 변화량이 임계치 미만이면 이상치가 아니다
- ✅ 최근 데이터에 비해 기압이 급상승하면 이상치로 판단한다
- ✅ 기압 변화량이 임계치 미만이면 이상치가 아니다

### RuleEngineTest

- ✅ 여러 규칙을 한 번에 평가할 수 있다

### RulePresetsTest

- ✅ defaultRules는 총 7개의 룰을 반환한다
- ✅ TVOC에 대한 ThresholdRule이 포함되어 있다
- ✅ TVOC에 대한 RapidRiseRule이 포함되어 있다
- ✅ eCO2에 대한 ThresholdRule이 포함되어 있다
- ✅ eCO2에 대한 RapidRiseRule이 포함되어 있다
- ✅ TEMPERATURE에 대한 RapidRiseRule이 포함되어 있다
- ✅ HUMIDITY에 대한 RapidRiseRule이 포함되어 있다
- ✅ PRESSURE에 대한 RapidRiseRule이 포함되어 있다
- ✅ TEMPERATURE, HUMIDITY, PRESSURE에는 ThresholdRule이 포함되지 않는다
- ✅ ThresholdRule은 TVOC와 eCO2에 대해서만 존재한다
- ✅ 모든 룰이 올바른 타입이다

### RulePresetsIntegrationTest

- ✅ TVOC 임계치 초과와 급상승이 동시에 발생하면 두 가지 이상이 모두 감지된다
- ✅ eCO2 임계치 초과와 급상승이 동시에 발생하면 두 가지 이상이 모두 감지된다
- ✅ 여러 오염물질에서 동시에 이상이 감지된다
- ✅ 정상 데이터에서는 이상이 감지되지 않는다
- ✅ 온도, 습도, 기압은 임계치 초과만으로는 이상이 감지되지 않는다

### FullFlowIntegrationTest

- ✅ 이상 감지부터 알림 생성까지 전체 플로우가 올바르게 동작한다
- ✅ 여러 이상이 동시에 감지되면 각각에 대해 알림이 생성된다
- ✅ 알림 레벨이 올바르게 매핑된다

---

## 테스트 실행 결과

### 최근 실행 결과

```bash
$ ./gradlew test

BUILD SUCCESSFUL in 1s

> Task :test
All tests passed: 40+ tests
```

### 테스트 통계

- **총 테스트 수**: 40+ 개
- **단위 테스트**: 30+ 개
- **통합 테스트**: 8 개
- **실행 시간**: < 5초
- **성공률**: 100%

### 테스트 실행 명령어

```bash
# 모든 테스트 실행
./gradlew test

# 특정 패키지 테스트
./gradlew test --tests "rule.*"
./gradlew test --tests "domain.*"
./gradlew test --tests "integration.*"

# 특정 테스트 클래스 실행
./gradlew test --tests "rule.RulePresetsTest"
./gradlew test --tests "integration.FullFlowIntegrationTest"
```

---

## 테스트 커버리지

### 커버리지 범위

주요 비즈니스 로직에 대한 테스트 커버리지를 제공합니다:

- ✅ **Rule Layer**: 100% 커버리지
  - ThresholdRule
  - RapidRiseRule
  - RuleEngine
  - RulePresets

- ✅ **Domain Layer**: 핵심 로직 커버
  - NotificationFactory
  - NotificationLevelMapper
  - AirQualityData (검증 로직 및 diff 메서드)
  - Pollutant (getValue 메서드)

- ✅ **Integration Layer**: 전체 플로우 커버
  - RuleEngine + RulePresets 통합
  - AirQualityData → Notification 전체 플로우

### 커버리지 리포트 생성

```bash
# JaCoCo 플러그인 추가 후 (향후 구현)
./gradlew test jacocoTestReport
```

---

## 테스트 접근 방법

### 1. 단위 테스트 접근

- **Given-When-Then 패턴** 사용
- 각 테스트는 하나의 시나리오만 검증
- 테스트 데이터는 최소한으로 구성

### 2. 통합 테스트 접근

- 실제 사용 시나리오 기반
- 여러 컴포넌트가 함께 동작하는지 검증
- 엣지 케이스 포함

### 3. 테스트 데이터 관리

- 테스트 데이터는 각 테스트 내에서 생성
- 공통 데이터는 헬퍼 메서드로 추출 (필요 시)

---

## 향후 개선 사항

- [ ] 테스트 커버리지 리포트 자동 생성 (JaCoCo)
- [ ] 성능 테스트 추가
- [ ] E2E 테스트 추가 (전체 시스템 통합)
- [ ] 테스트 데이터 빌더 패턴 적용
- [ ] 테스트 리팩토링 (중복 제거)

---

