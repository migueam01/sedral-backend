package com.uce.sedral.services.impl;

import com.uce.sedral.models.dto.PerfilDTO;
import com.uce.sedral.repositories.IPerfilRepo;
import com.uce.sedral.services.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements IPerfilService {

    private final IPerfilRepo repo;

    @Override
    public List<PerfilDTO> obtenerPerfil(List<Integer> idsPozos) {
        List<PerfilDTO> perfiles = new ArrayList<>();
        if (idsPozos == null || idsPozos.size() < 2) {
            return perfiles;
        }
        for (int i = 0; i < idsPozos.size() - 1; i++) {
            Integer pozoInicio = idsPozos.get(i);
            Integer pozoFin = idsPozos.get(i + 1);
            repo.obtenerPerfilTramo(pozoInicio, pozoFin).forEach(x -> {
                PerfilDTO perfilDTO = new PerfilDTO();
                perfilDTO.setIdPozoInicio(Integer.parseInt(String.valueOf(x[0])));
                perfilDTO.setNombrePozoInicio(String.valueOf(x[1]));
                perfilDTO.setAlturaInicio(Double.parseDouble(String.valueOf(x[2])));
                perfilDTO.setCotaTerrenoInicio(Double.parseDouble(String.valueOf(x[3])));
                perfilDTO.setIdPozoFin(Integer.parseInt(String.valueOf(x[4])));
                perfilDTO.setNombrePozoFin(String.valueOf(x[5]));
                perfilDTO.setAlturaFin(Double.parseDouble(String.valueOf(x[6])));
                perfilDTO.setCotaTerrenoFin(Double.parseDouble(String.valueOf(x[7])));
                perfilDTO.setPendiente(Double.parseDouble(String.valueOf(x[8])));
                perfilDTO.setCaudal(Double.parseDouble(String.valueOf(x[9])));
                perfilDTO.setVelocidad(Double.parseDouble(String.valueOf(x[10])));
                perfilDTO.setDiametro(Integer.parseInt(String.valueOf(x[11])));
                perfilDTO.setMaterial(String.valueOf(x[12]));
                perfilDTO.setLongitud(Double.parseDouble(String.valueOf(x[13])));
                perfiles.add(perfilDTO);
            });
        }
        return perfiles;
    }
}
