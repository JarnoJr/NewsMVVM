package com.example.newsmvvm.business.domain.util

interface DTOMapper<DTO,Entity> {

    fun mapToEntity(dto:DTO):Entity
}