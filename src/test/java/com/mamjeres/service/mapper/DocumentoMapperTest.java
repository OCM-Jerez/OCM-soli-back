package com.mamjeres.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentoMapperTest {
    private DocumentoMapper documentoMapper;

    @BeforeEach
    public void setUp() {
        documentoMapper = new DocumentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentoMapper.fromId(null)).isNull();
    }
}
