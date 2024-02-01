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
            Subcategory("경제 및 공공 재정"),
            Subcategory("상업"),
            Subcategory("금융 및 금융 부문"),
            Subcategory("외국 무역 및 국제 금융"),
            Subcategory("세금")
        ), color = "#779ECB"
    ),

    Category(
        "사회 및 문화", listOf(
            Subcategory("예술, 문화, 종교"),
            Subcategory("사회 복지"),
            Subcategory("가족"),
            Subcategory("교육"),
            Subcategory("사회과학 및 역사")
        ), color = "#AEC6CF"
    ),

    Category(
        "건강 및 환경", listOf(
            Subcategory("건강"),
            Subcategory("환경 보호"),
            Subcategory("주택 및 지역사회 개발"),
            Subcategory("공공 토지 및 천연 자원"),
            Subcategory("수자원 개발")
        ), color = "#77DD77"
    ),

    Category(
        "과학 및 기술", listOf(
            Subcategory("과학, 기술, 커뮤니케이션"),
            Subcategory("에너지"),
            Subcategory("비상 관리"),
            Subcategory("수자원 개발"),
            Subcategory("교통 및 공공 사업")
        ), color = "#fbfb46"
    ),

    Category(
        "안보 및 법 집행", listOf(
            Subcategory("국방 및 국가 안보"),
            Subcategory("범죄 및 법 집행"),
            Subcategory("이민"),
            Subcategory("노동 및 고용"),
            Subcategory("민간권 및 자유, 소수자 문제")
        ), color = "#FFB347"
    ),

    Category(
        "자연 및 생태", listOf(
            Subcategory("농업 및 식품"),
            Subcategory("동물"),
            Subcategory("스포츠 및 레크리에이션"),
            Subcategory("공공 토지 및 천연 자원"),
            Subcategory("환경 보호")
        ), color = "#FF6961"
    ),

    Category(
        "기타", listOf(Subcategory("기타")), color = "#E0E0E0"
    )
)