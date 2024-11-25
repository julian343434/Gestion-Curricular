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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





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

    @GetMapping("/planEstudio")
    @PreAuthorize("hasRole('Administrador')") 
    public List<PlanEstudioEntidad> obenerPlanEstudio(){
        return planEstudioServicio.ObtenerPlanEstudio();
    }

    @PostMapping("/guardarArchivo")
    @PreAuthorize("hasRole('Administrador')") 
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
            perteneceServicio.guardarRelacionArchivo(relaciones);

            // Devolver la lista de cursos creados
            return cursos;

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
        }
    }
    
    @PostMapping("/guardarCurso")
    @PreAuthorize("hasRole('Administrador')") 
    public CursoEntidad crearCurso(@RequestBody Map<String, Object> datos) {
        
        PlanEstudioEntidad planEstudioEntidad = planEstudioServicio.buscarId(((Number) datos.get("planEstudio")).longValue());

        datos.remove("planEstudio");

        int anio = (int) datos.get("anio");

        CursoEntidad cursoEntidad = cursoServicio.crearCurso(datos);

        PerteneceEntidad perteneceEntidad = perteneceServicio.crearRelacion(planEstudioEntidad.getId_plan_estudio(), cursoEntidad.getId_curso(), anio);

        perteneceEntidad.setCurso(cursoEntidad);
        perteneceEntidad.setPlanEstudio(planEstudioEntidad);

        perteneceServicio.guardarRelacion(perteneceEntidad);

        cursoEntidad.getplanEstudio().add(perteneceEntidad);
        
        planEstudioEntidad.getCursos().add(perteneceEntidad);

        return cursoEntidad;
    }
    
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')") // Restringe el acceso solo a usuarios con rol de Administrador
    public CursoEntidad actulizarCurso(@PathVariable Long id, @RequestBody Map<String, Object> campos){

        CursoEntidad curso = cursoServicio.obtenerCursoId(id);

        if(campos.containsKey("planEstudio")){
            PlanEstudioEntidad planEstudioEntidad = planEstudioServicio.buscarId(((Number) campos.get("planEstudio")).longValue());
            if(!campos.containsKey("anio")){
                throw new RuntimeException("Informacion insuficiente para cambiar el plan de estudio falta anio");
            }
            PerteneceEntidad perteneceEntidad = perteneceServicio.crearRelacion(planEstudioEntidad.getId_plan_estudio(), id, (int) campos.get("anio"));

            campos.remove("planEstudio");
            campos.remove("anio");

            perteneceEntidad.setPlanEstudio(planEstudioEntidad);
            perteneceEntidad.setCurso(curso);

            curso.getplanEstudio().add(perteneceEntidad);
            perteneceServicio.guardarRelacion(perteneceEntidad);
        }

        if(!campos.isEmpty()){
            cursoServicio.actulizarCurso(curso, campos);
        }
        
        return curso;
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
