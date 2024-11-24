package com.example.demo.controlador.PlanEstudio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.modelo.PlanEstudio.servicios.CursoServicio;
import com.example.demo.modelo.PlanEstudio.entidades.CursoEntidad;
import com.example.demo.modelo.PlanEstudio.entidades.PerteneceEntidad;
import com.example.demo.modelo.PlanEstudio.entidades.PlanEstudioEntidad;
import com.example.demo.modelo.PlanEstudio.servicios.PerteneceServicio;
import com.example.demo.modelo.PlanEstudio.servicios.PlanEstudioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/planEstudio")
public class PlanEstudio {


    @Autowired
    private CursoServicio cursoServicio;
    @Autowired
    private PlanEstudioServicio planEstudioServicio;
    @Autowired
    private PerteneceServicio perteneceServicio;

    @GetMapping("/")
    @PreAuthorize("hasRole('Administrador')") 
    public List<CursoEntidad> obenerCursos(){
        return cursoServicio.ObtenerCurso();
    }

    @PostMapping("/guardarArchivo")
    public List<CursoEntidad> manejoArchivo(@RequestParam("file") MultipartFile archivo,
                                        @RequestParam("nombre") String nombre,
                                        @RequestParam("descripcion") String descripcion,
                                        @RequestParam("anio") int anio) {
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        try (InputStream inputStream = archivo.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {

                if (archivo.getSize() == 0) {
                    throw new RuntimeException("El archivo subido está vacío.");
                }
                
            // Obtener la primera hoja del archivo Excel
            Sheet hoja = workbook.getSheetAt(0);
            if (hoja == null) {
                throw new RuntimeException("La hoja está vacía");
            }

            // Validar si el archivo es Excel
            validarSiEsExcel(archivo);

            // Crear la entidad PlanEstudio
            Map<String, Object> datos = new HashMap<>();
            datos.put("archivo", archivo.getBytes());
            datos.put("nombre", nombre);
            datos.put("descripcion", descripcion);

            PlanEstudioEntidad planEstudioEntidad = planEstudioServicio.guardarPlanEstudio(datos);

            // Crear los cursos a partir del contenido de la hoja
            List<CursoEntidad> cursos = cursoServicio.crearCursoArchivo(hoja);
            List<Long> id_cursos = new ArrayList<>();
            for (CursoEntidad curso : cursos) {
                id_cursos.add(curso.getId_curso());
            }

            // Crear las relaciones entre PlanEstudio y Cursos
            List<PerteneceEntidad> relaciones = perteneceServicio.crearRelacionArchivo(
                planEstudioEntidad.getId_plan_estudio(), id_cursos, anio);

            // Vincular relaciones con cursos y el plan de estudio
            for (int i = 0; i < cursos.size(); i++) {
                PerteneceEntidad relacion = relaciones.get(i);
                CursoEntidad curso = cursos.get(i);

                relacion.setCurso(curso);
                relacion.setPlanEstudio(planEstudioEntidad);
                
                planEstudioEntidad.getCursos().add(relacion);
                curso.getplanEstudio().add(relacion);
            }

            // Guardar relaciones
            perteneceServicio.guardarRelacion(relaciones);

            // Devolver la lista de cursos creados
            return cursos;

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
        }
    }
    // Método de validación
    private void validarSiEsExcel(MultipartFile archivo) {
        String tipoContenido = archivo.getContentType();

        if (!"application/vnd.ms-excel".equals(tipoContenido) && 
            !"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(tipoContenido)) {
            throw new RuntimeException("El archivo no es un Excel válido. Asegúrate de subir un archivo .xls o .xlsx");
        }
    }
    

}
