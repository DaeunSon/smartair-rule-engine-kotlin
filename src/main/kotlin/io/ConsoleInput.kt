package com.daeun.smartair.io

class ConsoleInput {

    /**
     * 콘솔에서 int 입력을 읽어오는 유틸 함수
     */
    public fun readIntInput(prompt: String): Int {
        while(true){
            print(prompt)
            val line = readlnOrNull()

            val value = line?.toIntOrNull()
            if (value != null) return value

            println("[Error] 유효한 정수를 입력해주세요.")
        }
    }

    /**
     * 콘솔에서 Double 입력을 읽어오는 유틸 함수
     */
    public fun readDoubleInput(prompt: String): Double {
        while (true) {
            print(prompt)
            val line = readlnOrNull()

            val value = line?.toDoubleOrNull()
            if (value != null) return value

            println("[Error] 유효한 실수를 입력해주세요.")
        }
    }


}