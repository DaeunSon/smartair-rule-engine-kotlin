package com.daeun.smartair.domain.report

enum class AnomalyType(
    val label: String,
    val description: String
) {
    THRESHOLD_EXCEEDED(
        label = "임계치 초과",
        description = "지정된 임계값을 초과하여 이상 상태가 감지되었습니다."
    ),

    RAPID_RISE(
        label = "급격한 상승",
        description = "단기간 내에 오염 물질 수치가 급격히 상승하였습니다."
    ),

    RAPID_DROP(
        label = "급격한 하락",
        description = "단기간 내에 오염 물질 수치가 급격히 하락하였습니다."
    ),

    SENSOR_ERROR(
        label = "센서 오류",
        description = "센서에서 비정상적인 데이터가 감지되었습니다. 센서 오류 가능성이 있습니다."
    );
}