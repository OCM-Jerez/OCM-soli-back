package com.mamjeres.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SolicitudMapperTest {
    private SolicitudMapper solicitudMapper;

    @BeforeEach
    public void setUp() {
        solicitudMapper = new SolicitudMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(solicitudMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(solicitudMapper.fromId(null)).isNull();
    }
}
