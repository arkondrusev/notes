package com.example.notes.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel="spring", unmappedTargetPolicy= ReportingPolicy.ERROR)
public interface NotesAppMapperConfig {



}
