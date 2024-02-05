package com.congressdatacontest.congressdatacontest_android.feature.category

import android.graphics.Color

fun getSubcategoryColor(categoryColor: String): String {
    val color = Color.parseColor(categoryColor)
    val r = (Color.red(color) + (255 - Color.red(color)) * 0.3).toInt()
    val g = (Color.green(color) + (255 - Color.green(color)) * 0.3).toInt()
    val b = (Color.blue(color) + (255 - Color.blue(color)) * 0.3).toInt()
    return String.format("#%02X%02X%02X", r, g, b)
}

fun getSubcategoryUnClickColor(categoryColor: String): String {
    val color = Color.parseColor(categoryColor)
    val r = (Color.red(color) + (255 - Color.red(color)) * 0.5).toInt()
    val g = (Color.green(color) + (255 - Color.green(color)) * 0.5).toInt()
    val b = (Color.blue(color) + (255 - Color.blue(color)) * 0.5).toInt()
    return String.format("#%02X%02X%02X", r, g, b)
}

val categories = listOf(
    Category(
        "경제 및 금융", listOf(
            Subcategory(1, "경제 및 공공 재정"),
            Subcategory(2, "상업"),
            Subcategory(3, "금융 및 금융 부문"),
            Subcategory(4, "외국 무역 및 국제 금융"),
            Subcategory(5, "세금")
        ), color = "#779ECB"
    ),

    Category(
        "사회 및 문화", listOf(
            Subcategory(6, "예술, 문화, 종교"),
            Subcategory(7, "사회 복지"),
            Subcategory(8, "가족"),
            Subcategory(9, "교육"),
            Subcategory(10, "사회과학 및 역사")
        ), color = "#AEC6CF"
    ),

    Category(
        "건강 및 환경", listOf(
            Subcategory(11, "건강"),
            Subcategory(12, "환경 보호"),
            Subcategory(13, "주택 및 지역사회 개발"),
            Subcategory(14, "공공 토지 및 천연 자원"),
            Subcategory(15, "수자원 개발")
        ), color = "#77DD77"
    ),

    Category(
        "과학 및 기술", listOf(
            Subcategory(16, "과학, 기술, 커뮤니케이션"),
            Subcategory(17, "에너지"),
            Subcategory(18, "비상 관리"),
            Subcategory(19, "수자원 개발"),
            Subcategory(20, "교통 및 공공 사업")
        ), color = "#fbfb46"
    ),

    Category(
        "안보 및 법 집행", listOf(
            Subcategory(21, "국방 및 국가 안보"),
            Subcategory(22, "범죄 및 법 집행"),
            Subcategory(23, "이민"),
            Subcategory(24, "노동 및 고용"),
            Subcategory(25, "민간권 및 자유, 소수자 문제")
        ), color = "#FFB347"
    ),

    Category(
        "자연 및 생태", listOf(
            Subcategory(26, "농업 및 식품"),
            Subcategory(27, "동물"),
            Subcategory(28, "스포츠 및 레크리에이션"),
            Subcategory(29, "공공 토지 및 천연 자원"),
            Subcategory(30, "환경 보호")
        ), color = "#FF6961"
    ),

    Category(
        "기타", listOf(Subcategory(0, "기타")), color = "#E0E0E0"
    )
)