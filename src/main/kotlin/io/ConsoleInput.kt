package com.daeun.smartair.io

class ConsoleInput {

    /**
     * 콘솔에서 int 입력을 읽어오는 유틸 함수
     */
     fun readIntInput(prompt: String): Int {
        while(true){
            print(prompt)
            val line = readLine()

            if (line == null) {
                println("\n[Error] 입력 스트림이 종료되었습니다.")
                throw IllegalStateException("입력 스트림이 종료되었습니다.")
            }

            val value = line.trim().toIntOrNull()
            if (value != null) return value

            println("[Error] 유효한 정수를 입력해주세요.")
        }
    }

    /**
     * 콘솔에서 Double 입력을 읽어오는 유틸 함수
     */
     fun readDoubleInput(prompt: String): Double {
        while (true) {
            print(prompt)
            val line = readLine()

            if (line == null) {
                println("\n[Error] 입력 스트림이 종료되었습니다.")
                throw IllegalStateException("입력 스트림이 종료되었습니다.")
            }

            val value = line.trim().toDoubleOrNull()
            if (value != null) return value

            println("[Error] 유효한 실수를 입력해주세요.")
        }
    }


}