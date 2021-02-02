package com.mamjeres.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mamjeres.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class GestionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GestionDTO.class);
        GestionDTO gestionDTO1 = new GestionDTO();
        gestionDTO1.setId(1L);
        GestionDTO gestionDTO2 = new GestionDTO();
        assertThat(gestionDTO1).isNotEqualTo(gestionDTO2);
        gestionDTO2.setId(gestionDTO1.getId());
        assertThat(gestionDTO1).isEqualTo(gestionDTO2);
        gestionDTO2.setId(2L);
        assertThat(gestionDTO1).isNotEqualTo(gestionDTO2);
        gestionDTO1.setId(null);
        assertThat(gestionDTO1).isNotEqualTo(gestionDTO2);
    }
}
