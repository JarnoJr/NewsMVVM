package com.example.newsmvvm.business.domain.util

interface EntityMapper<Entity, Domain> {

    fun mapFromEntity(entity: Entity): Domain

    fun mapToEntity(domain: Domain): Entity
}