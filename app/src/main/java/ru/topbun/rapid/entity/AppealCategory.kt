package ru.topbun.rapid.entity

enum class AppealCategory(val description: String) {
    ROAD("Проблемы с дорогой"),
    LIGHTING("Неисправное освещение"),
    CLEANING("Неубранная территория"),
    WATER_SUPPLY("Проблемы с водоснабжением"),
    SEWAGE("Проблемы с канализацией"),
    TRASH("Несвоевременный вывоз мусора"),
    NOISE("Шумовое загрязнение"),
    PARKING("Проблемы с парковкой"),
    ILLEGAL_CONSTRUCTION("Незаконная постройка"),
    GREEN_SPACES("Ухудшение состояния зеленых зон"),
    PUBLIC_TRANSPORT("Проблемы с общественным транспортом"),
    SAFETY("Опасные условия или криминогенная обстановка"),
    INFRASTRUCTURE("Нарушение инфраструктуры"),
    WILDLIFE("Дикие животные в жилых районах"),
    OTHER("Другая проблема");

    override fun toString(): String {
        return description
    }

    companion object{
        fun fromString(str: String) = when (str) {
            "Проблемы с дорогой" -> ROAD
            "Неисправное освещение" -> LIGHTING
            "Неубранная территория" -> CLEANING
            "Проблемы с водоснабжением" -> WATER_SUPPLY
            "Проблемы с канализацией" -> SEWAGE
            "Несвоевременный вывоз мусора" -> TRASH
            "Шумовое загрязнение" -> NOISE
            "Проблемы с парковкой" -> PARKING
            "Незаконная постройка" -> ILLEGAL_CONSTRUCTION
            "Ухудшение состояния зеленых зон" -> GREEN_SPACES
            "Проблемы с общественным транспортом" -> PUBLIC_TRANSPORT
            "Опасные условия или криминогенная обстановка" -> SAFETY
            "Нарушение инфраструктуры" -> INFRASTRUCTURE
            "Дикие животные в жилых районах" -> WILDLIFE
            "Другая проблема" -> OTHER
            else -> OTHER
        }
    }


}