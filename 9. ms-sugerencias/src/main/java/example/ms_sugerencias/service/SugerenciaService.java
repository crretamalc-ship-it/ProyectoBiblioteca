package example.ms_sugerencias.service;

import example.ms_sugerencias.dto.SugerenciaEstadoDTO;
import example.ms_sugerencias.dto.SugerenciaRequestDTO;
import example.ms_sugerencias.dto.SugerenciaResponseDTO;
import java.util.List;

public interface SugerenciaService {
    SugerenciaResponseDTO crear(SugerenciaRequestDTO dto);
    List<SugerenciaResponseDTO> listar();
    List<SugerenciaResponseDTO> listarPorSocio(String email);
    List<SugerenciaResponseDTO> listarPorEstado(String estado);
    SugerenciaResponseDTO buscarPorId(Long id);
    SugerenciaResponseDTO actualizar(Long id, SugerenciaRequestDTO dto);
    SugerenciaResponseDTO cambiarEstado(Long id, SugerenciaEstadoDTO estado);
    void eliminar(Long id);
}
