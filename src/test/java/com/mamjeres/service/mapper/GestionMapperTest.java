package com.mamjeres.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestionMapperTest {
    private GestionMapper gestionMapper;

    @BeforeEach
    public void setUp() {
        gestionMapper = new GestionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gestionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gestionMapper.fromId(null)).isNull();
    }
}
