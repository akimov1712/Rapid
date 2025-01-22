package ru.topbun.rapid.entity

enum class AppealStatus {

    Progress, Success, Failed;

    override fun toString() = when(this){
        Progress -> "В процессе"
        Success -> "Принято"
        Failed -> "Отказано"
    }
}