package com.example.newsmvvm.business.domain.util

interface DTOMapper<DTO,T> {

    fun mapfromDto(dto:DTO):T
}