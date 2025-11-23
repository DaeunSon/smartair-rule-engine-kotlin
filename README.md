# SmartAir Rule Engine

공기질 데이터를 분석하여 이상 패턴을 감지하고 알림을 생성하는 룰 엔진 시스템입니다.

## 📋 목차

- [프로젝트 개요](#프로젝트-개요)
- [주요 기능](#주요-기능)
- [프로젝트 구조](#프로젝트-구조)
- [빌드 및 실행](#빌드-및-실행)
- [사용 예시](#사용-예시)
- [테스트](#테스트)

---

## 프로젝트 개요

SmartAir Rule Engine은 실내 공기질 센서 데이터를 실시간으로 분석하여 이상 패턴을 감지하고, 사용자에게 적절한 알림을 제공하는 시스템입니다.

### 주요 목적

- 공기질 데이터의 이상 패턴 자동 감지
- 오염물질별 맞춤형 룰 적용
- 이상 감지 시 자동 알림 생성
- 확장 가능한 룰 시스템 제공

---

## 주요 기능

### 1. 이상 패턴 감지

#### 임계치 초과 감지 (Threshold Rule)
- **TVOC**: 600 ppb 초과 시 경고
- **eCO2**: 1000 ppm 초과 시 경고

#### 급상승 감지 (Rapid Rise Rule)
- **온도**: 5°C 이상 상승 시 경고
- **습도**: 15% 이상 상승 시 경고
- **기압**: 8 hPa 이상 상승 시 경고
- **TVOC**: 300 ppb 이상 상승 시 경고
- **eCO2**: 400 ppm 이상 상승 시 경고

### 2. 알림 시스템

이상 감지 시 자동으로 알림을 생성하며, 이상 유형에 따라 다른 레벨의 알림을 제공합니다:

- **WARNING**: 임계치 초과 (THRESHOLD_EXCEEDED)
- **DANGER**: 급상승 (RAPID_RISE)
- **INFO**: 센서 오류 (SENSOR_ERROR)

### 3. 확장 가능한 룰 시스템

- 룰 기반 아키텍처로 새로운 룰 추가 용이
- 오염물질별 맞춤형 룰 적용
- 룰 프리셋 제공 (`RulePresets`)

---

## 프로젝트 구조

```
smartair-rule-engine-kotlin/
├── src/
│   ├── main/kotlin/
│   │   ├── domain/
│   │   │   ├── data/              # 도메인 데이터 모델
│   │   │   │   ├── AirQualityData.kt
│   │   │   │   └── Pollutant.kt
│   │   │   ├── notification/      # 알림 도메인
│   │   │   │   ├── Notification.kt
│   │   │   │   ├── NotificationFactory.kt
│   │   │   │   ├── NotificationLevel.kt
│   │   │   │   └── NotificationLevelMapper.kt
│   │   │   └── report/             # 리포트 도메인
│   │   │       ├── AnomalyReport.kt
│   │   │       ├── AnomalyResult.kt
│   │   │       └── AnomalyType.kt
│   │   ├── rule/                  # 룰 엔진
│   │   │   ├── AnomalyRule.kt
│   │   │   ├── RapidRiseRule.kt
│   │   │   ├── RuleEngine.kt
│   │   │   ├── RulePresets.kt
│   │   │   └── ThresholdRule.kt
│   │   ├── io/                    # 입출력
│   │   │   └── ConsoleInput.kt
│   │   └── Main.kt                # 메인 진입점
│   └── test/kotlin/
│       ├── domain/                 # 도메인 테스트
│       ├── rule/                   # 룰 테스트
│       └── integration/            # 통합 테스트
├── build.gradle.kts
└── README.md
```

---

## 빌드 및 실행

### 요구사항

- JDK 17 이상
- Gradle 7.0 이상

### 빌드

```bash
./gradlew build
```

### 테스트 실행

```bash
# 모든 테스트 실행
./gradlew test

# 특정 테스트 실행
./gradlew test --tests "rule.RulePresetsTest"
```

### 애플리케이션 실행

```bash
./gradlew run
```

또는 IDE에서 `Main.kt`를 실행합니다.

---

## 사용 예시

### CLI 실행 예시

```bash
=== SmartAir RuleEngine Console Demo ===
사용자가 입력한 공기질 데이터를 기반으로 이상치 감지 / 리포트 / 알림을 생성합니다.

몇 개의 측정값을 입력하시겠습니까? (최소 2개 이상): 2

각 측정값은 최근 값일수록 나중에 입력된다고 가정합니다.
예: 1번째 = 과거 데이터, 마지막 = 가장 최근 데이터

[1/2] 측정값을 입력합니다.
  - 온도(℃)를 입력해주세요 (예: 24.5): 24.0
  - 습도(%)를 입력해주세요 (예: 45.0): 40.0
  - 기압(hPa)을 입력해주세요 (예: 1013.0): 1013.0
  - TVOC 값을 입력해주세요 (예: 350.0): 300.0
  - eCO₂ 값을 입력해주세요 (예: 800.0): 500.0

[2/2] 측정값을 입력합니다.
  - 온도(℃)를 입력해주세요 (예: 24.5): 24.0
  - 습도(%)를 입력해주세요 (예: 45.0): 40.0
  - 기압(hPa)을 입력해주세요 (예: 1013.0): 1013.0
  - TVOC 값을 입력해주세요 (예: 350.0): 700.0
  - eCO₂ 값을 입력해주세요 (예: 800.0): 500.0

입력한 측정값 요약:
  [0] time=2025-01-XX..., temp=24.0, hum=40.0, pres=1013.0, tvoc=300.0, eco2=500.0
  [1] time=2025-01-XX..., temp=24.0, hum=40.0, pres=1013.0, tvoc=700.0, eco2=500.0

[1] AnomalyResult 결과
  - AnomalyResult(pollutant=TVOC, type=THRESHOLD_EXCEEDED, value=700.0, message=...)
  - AnomalyResult(pollutant=TVOC, type=RAPID_RISE, value=400.0, message=...)

[2] AnomalyReport 결과
  - AnomalyReport(...)

[3] Notification 결과
  - level=WARNING, title=공기질 임계치 초과 감지
  - message=[임계치 초과] TVOC 이상이 감지되었습니다. 측정값: 700.0 (...)
  - level=DANGER, title=공기질 급상승 이상 감지
  - message=[급격한 상승] TVOC 이상이 감지되었습니다. 측정값: 400.0 (...)
```

### 코드 사용 예시

```kotlin
import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.rule.RuleEngine
import com.daeun.smartair.rule.RulePresets
import java.time.LocalDateTime

// 1. 룰 엔진 초기화
val rules = RulePresets.defaultRules()
val engine = RuleEngine(rules)

// 2. 공기질 데이터 준비
val history = listOf(
    AirQualityData("sensor-1", LocalDateTime.now().minusMinutes(5), 
                   24.0, 40.0, 1013.0, 300.0, 500.0)
)

val current = AirQualityData("sensor-1", LocalDateTime.now(),
                             24.0, 40.0, 1013.0, 700.0, 500.0)

// 3. 이상 감지
val results = engine.evaluate(current, history)

// 4. 결과 처리
results.forEach { result ->
    println("${result.pollutant}: ${result.type} - ${result.message}")
}
```

---

## 테스트

### 테스트 구조

- **단위 테스트**: 각 클래스의 개별 기능 테스트
- **통합 테스트**: 여러 컴포넌트 간의 상호작용 테스트

### 테스트 실행

```bash
# 모든 테스트 실행
./gradlew test

# 특정 패키지 테스트
./gradlew test --tests "rule.*"

# 특정 테스트 클래스 실행
./gradlew test --tests "rule.RulePresetsTest"
```

### 테스트 커버리지

주요 도메인 및 룰 엔진 로직에 대한 테스트 커버리지를 제공합니다.

---

## 기술 스택

- **언어**: Kotlin 2.2.20
- **빌드 도구**: Gradle
- **테스트 프레임워크**: Kotest 5.9.1
- **JDK**: 17

---

