# CLI 시연 화면

## 📋 목차

- [기본 실행 예시](#기본-실행-예시)
- [이상 감지 시나리오](#이상-감지-시나리오)
- [정상 데이터 시나리오](#정상-데이터-시나리오)
- [여러 이상 동시 감지](#여러-이상-동시-감지)

---

## 기본 실행 예시

### 실행 방법

```bash
./gradlew run
```

또는 IDE에서 `Main.kt`를 실행합니다.

---

## 이상 감지 시나리오

### 시나리오 1: TVOC 임계치 초과 및 급상승

**입력 데이터**:
- 측정값 2개 입력
- 첫 번째: TVOC 300.0
- 두 번째: TVOC 700.0 (임계치 600 초과, 400 상승)

**실행 결과**:

```
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
  - AnomalyResult(pollutant=TVOC, type=THRESHOLD_EXCEEDED, value=700.0, message=TVOC 값이 임계치 600.0 를 초과했습니다. (현재: 700.0))
  - AnomalyResult(pollutant=TVOC, type=RAPID_RISE, value=400.0, message=TVOC 값이 짧은 시간 동안 400.0 증가했습니다.)

[2] AnomalyReport 결과
  - AnomalyReport(id=null, sensorId=sensor-1, anomalyTimeStamp=2025-01-XX..., pollutant=TVOC, pollutantValue=700.0, type=THRESHOLD_EXCEEDED, description=TVOC 값이 임계치 600.0 를 초과했습니다. (현재: 700.0), ...)
  - AnomalyReport(id=null, sensorId=sensor-1, anomalyTimeStamp=2025-01-XX..., pollutant=TVOC, pollutantValue=400.0, type=RAPID_RISE, description=TVOC 값이 짧은 시간 동안 400.0 증가했습니다., ...)

[3] Notification 결과
  - level=WARNING, title=공기질 임계치 초과 감지
  - message=[임계치 초과] TVOC 이상이 감지되었습니다. 측정값: 700.0 (TVOC 값이 임계치 600.0 를 초과했습니다. (현재: 700.0))
  - level=DANGER, title=공기질 급상승 이상 감지
  - message=[급격한 상승] TVOC 이상이 감지되었습니다. 측정값: 400.0 (TVOC 값이 짧은 시간 동안 400.0 증가했습니다.)
```

**분석**:
- TVOC가 300에서 700으로 상승 (400 상승 > 300 기준)
- TVOC가 700으로 임계치 600 초과
- 두 가지 이상이 동시에 감지되어 WARNING과 DANGER 알림이 각각 생성됨

---

## 정상 데이터 시나리오

### 시나리오 2: 정상 데이터 (이상 없음)

**입력 데이터**:
- 측정값 2개 입력
- 모든 값이 정상 범위 내

**실행 결과**:

```
=== SmartAir RuleEngine Console Demo ===
사용자가 입력한 공기질 데이터를 기반으로 이상치 감지 / 리포트 / 알림을 생성합니다.

몇 개의 측정값을 입력하시겠습니까? (최소 2개 이상): 2

[1/2] 측정값을 입력합니다.
  - 온도(℃)를 입력해주세요 (예: 24.5): 24.0
  - 습도(%)를 입력해주세요 (예: 45.0): 40.0
  - 기압(hPa)을 입력해주세요 (예: 1013.0): 1013.0
  - TVOC 값을 입력해주세요 (예: 350.0): 300.0
  - eCO₂ 값을 입력해주세요 (예: 800.0): 500.0

[2/2] 측정값을 입력합니다.
  - 온도(℃)를 입력해주세요 (예: 24.5): 24.5
  - 습도(%)를 입력해주세요 (예: 45.0): 42.0
  - 기압(hPa)을 입력해주세요 (예: 1013.0): 1014.0
  - TVOC 값을 입력해주세요 (예: 350.0): 350.0
  - eCO₂ 값을 입력해주세요 (예: 800.0): 600.0

입력한 측정값 요약:
  [0] time=2025-01-XX..., temp=24.0, hum=40.0, pres=1013.0, tvoc=300.0, eco2=500.0
  [1] time=2025-01-XX..., temp=24.5, hum=42.0, pres=1014.0, tvoc=350.0, eco2=600.0

[1] AnomalyResult 결과
  - 이상치가 감지되지 않았습니다.
```

**분석**:
- 모든 값이 정상 범위 내
- 변화량도 급상승 기준 미만
- 이상 감지 없음

---

## 여러 이상 동시 감지

### 시나리오 3: 여러 오염물질 동시 이상 감지

**입력 데이터**:
- 측정값 2개 입력
- 온도 급상승 (24 → 30)
- 습도 급상승 (40 → 55)
- 기압 급상승 (1013 → 1021)
- TVOC 임계치 초과 + 급상승 (300 → 700)
- eCO2 임계치 초과 + 급상승 (500 → 1100)

**실행 결과**:

```
=== SmartAir RuleEngine Console Demo ===
...

[1] AnomalyResult 결과
  - AnomalyResult(pollutant=TEMPERATURE, type=RAPID_RISE, value=6.0, message=Temperature 값이 짧은 시간 동안 6.0 증가했습니다.)
  - AnomalyResult(pollutant=HUMIDITY, type=RAPID_RISE, value=15.0, message=Humidity 값이 짧은 시간 동안 15.0 증가했습니다.)
  - AnomalyResult(pollutant=PRESSURE, type=RAPID_RISE, value=8.0, message=Pressure 값이 짧은 시간 동안 8.0 증가했습니다.)
  - AnomalyResult(pollutant=TVOC, type=THRESHOLD_EXCEEDED, value=700.0, message=TVOC 값이 임계치 600.0 를 초과했습니다. (현재: 700.0))
  - AnomalyResult(pollutant=TVOC, type=RAPID_RISE, value=400.0, message=TVOC 값이 짧은 시간 동안 400.0 증가했습니다.)
  - AnomalyResult(pollutant=ECO2, type=THRESHOLD_EXCEEDED, value=1100.0, message=eCO2 값이 임계치 1000.0 를 초과했습니다. (현재: 1100.0))
  - AnomalyResult(pollutant=ECO2, type=RAPID_RISE, value=600.0, message=eCO2 값이 짧은 시간 동안 600.0 증가했습니다.)

[2] AnomalyReport 결과
  - (7개의 리포트 생성)

[3] Notification 결과
  - level=WARNING, title=공기질 임계치 초과 감지 (TVOC)
  - level=WARNING, title=공기질 임계치 초과 감지 (eCO2)
  - level=DANGER, title=공기질 급상승 이상 감지 (온도)
  - level=DANGER, title=공기질 급상승 이상 감지 (습도)
  - level=DANGER, title=공기질 급상승 이상 감지 (기압)
  - level=DANGER, title=공기질 급상승 이상 감지 (TVOC)
  - level=DANGER, title=공기질 급상승 이상 감지 (eCO2)
```

**분석**:
- 총 7개의 이상이 감지됨
- WARNING 알림 2개 (TVOC, eCO2 임계치 초과)
- DANGER 알림 5개 (온도, 습도, 기압, TVOC, eCO2 급상승)
- 각 이상에 대해 개별 알림 생성

---

## 사용 가이드

### 입력 데이터 형식

각 측정값은 다음 정보를 포함합니다:
- **온도** (℃): 실내 온도
- **습도** (%): 실내 습도
- **기압** (hPa): 대기압
- **TVOC** (ppb): 휘발성 유기 화합물
- **eCO2** (ppm): 등가 이산화탄소

### 주의사항

1. **최소 2개 이상의 측정값 필요**: 급상승 감지를 위해 과거 데이터가 필요합니다
2. **시간 순서**: 첫 번째 입력이 과거 데이터, 마지막 입력이 최근 데이터입니다
3. **입력 형식**: 숫자만 입력 (소수점 가능)

### 이상 감지 기준

- **TVOC**: 600 ppb 초과 또는 300 ppb 이상 상승
- **eCO2**: 1000 ppm 초과 또는 400 ppm 이상 상승
- **온도**: 5℃ 이상 상승
- **습도**: 15% 이상 상승
- **기압**: 8 hPa 이상 상승

---

## 참고

- 실제 센서 데이터를 시뮬레이션하는 데 사용할 수 있습니다
- 다양한 시나리오를 테스트하여 룰 엔진의 동작을 확인할 수 있습니다
- 알림 레벨과 메시지를 확인하여 사용자 경험을 개선할 수 있습니다

