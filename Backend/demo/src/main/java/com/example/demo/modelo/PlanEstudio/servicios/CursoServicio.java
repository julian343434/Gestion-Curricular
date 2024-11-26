package com.example.demo.modelo.PlanEstudio.servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.demo.modelo.PlanEstudio.repositorios.CursoRepositorio;
import com.example.demo.modelo.PlanEstudio.entidades.CursoEntidad;

@Service
public class CursoServicio {

    @Autowired
    private CursoRepositorio cursoRepositorio;

    private static Map<Character, Integer> numerosRomanos = new HashMap<>();

    static{
        numerosRomanos.put('I', 1);
        numerosRomanos.put('V', 5);
        numerosRomanos.put('X', 10);
    }
    
    // Obtiene todos los cursos registrados
    public List<CursoEntidad> ObtenerCurso(){
        List<CursoEntidad> cursos = cursoRepositorio.findAll();
        if(cursos.isEmpty()){
            throw new RuntimeException("Sin cursos registrados");
        }
        return cursos;
    }

    public  CursoEntidad obtenerCursoId(Long id){
        return cursoRepositorio.findById(id).orElseThrow(() -> new RuntimeException("curso " + id + " no encontrado"));
    }

    // Obtiene todos los cursos registrados a un plan de estudio
    public List<CursoEntidad> ObtenerCursoPorPlanEstudio(Long id){
        List<CursoEntidad> cursos = cursoRepositorio.findCursosByPlanEstudio(id);
        if(cursos.isEmpty()){
            throw new RuntimeException("Sin cursos registrados");
        }
        return cursos;
    }

    // Crear y guardar muchos cursos
    public List<CursoEntidad> crearCursoArchivo(Sheet hoja){
        List<CursoEntidad> cursos = new ArrayList<>();

        Map<String, Object> datos = new HashMap<>();

        for (int i = 4; i < hoja.getLastRowNum(); i++){
            Row fila = hoja.getRow(i);

            if(fila == null){
                continue;
            }
            if(esCombinada(hoja, fila.getCell(0))){
                datos.put("semestre", semestre(getVarlorCombinada(hoja, fila.getCell(0))));
            }else{
                if (fila.getCell(0).getStringCellValue().contains("Total")) {
                    continue;
                }
                datos.put("nombre", getCellValue(fila.getCell(0)));
        
                datos.put("obligatorio", fila.getCell(1) == null || getCellValue(fila.getCell(1)).toString().trim().isEmpty() ? false : true);
        
                datos.put("creditos", getCellValue(fila.getCell(3)));
                datos.put("relacion", getCellValue(fila.getCell(4)));
                datos.put("tipo", getCellValue(fila.getCell(5)));
                datos.put("horasDeTrabajo", getCellValue(fila.getCell(6)));
        
                if (!getCellValue(fila.getCell(9)).toString().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Basica");
                } else if (!getCellValue(fila.getCell(10)).toString().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Especifica");
                } else if (!getCellValue(fila.getCell(11)).toString().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Investigacion");
                } else if (!getCellValue(fila.getCell(12)).toString().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Complementaria");
                }
        
                datos.put("maxEstudiantes", getCellValue(fila.getCell(13)));
                
                CursoEntidad curso = new CursoEntidad(
                    obtenerEntero(datos, "semestre", 1),       // Convierte seguro a Integer
                    obtenerString(datos, "nombre", "Sin nombre"), // Convierte seguro a String
                    obtenerBooleano(datos, "obligatorio", false), // Convierte seguro a Boolean
                    obtenerEntero(datos, "creditos", 1),          // Convierte seguro a Integer
                    obtenerString(datos, "relacion", ""),         // Convierte seguro a String
                    obtenerString(datos, "tipo", ""),             // Convierte seguro a String
                    obtenerEntero(datos, "horasDeTrabajo", 0),    // Convierte seguro a Integer
                    obtenerString(datos, "areaDeFormacion", ""),  // Convierte seguro a String
                    obtenerEntero(datos, "maxEstudiantes", 0),    // Convierte seguro a Integer
                    new ArrayList<>()
                );

                // Guardar la entidad Curso
                cursos.add(cursoRepositorio.save(curso));

                
            }

        }

        return cursos;
    }
    
    private Integer obtenerEntero(Map<String, Object> datos, String clave, Integer valorPorDefecto) {
        Object valor = datos.getOrDefault(clave, valorPorDefecto);
        if (valor instanceof Integer) {
            return (Integer) valor;
        } else if (valor instanceof Double) {
            return ((Double) valor).intValue(); // Convierte Double a Integer
        }
        return valorPorDefecto;
    }
    
    private Boolean obtenerBooleano(Map<String, Object> datos, String clave, Boolean valorPorDefecto) {
        Object valor = datos.getOrDefault(clave, valorPorDefecto);
        if (valor instanceof Boolean) {
            return (Boolean) valor;
        }
        return valorPorDefecto;
    }
    
    private String obtenerString(Map<String, Object> datos, String clave, String valorPorDefecto) {
        Object valor = datos.getOrDefault(clave, valorPorDefecto);
        if (valor instanceof String) {

            String texto = (String) valor;
             // Verifica y elimina comillas dobles solo si están al inicio y al final
            if (texto.startsWith("\"") && texto.endsWith("\"")) {
                return texto.substring(1, texto.length() - 1);
            }
            return texto;
        }
        return valorPorDefecto;
    }

    // Método auxiliar para obtener valores de celdas dinámicamente
    private Object getCellValue(Cell celda) {
        if (celda == null) {
            return "";
        }
        switch (celda.getCellType()) {
            case STRING:
                return celda.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celda)) {
                    return celda.getDateCellValue();
                } else {
                    return celda.getNumericCellValue();
                }
            case BOOLEAN:
                return celda.getBooleanCellValue();
            case FORMULA:
                return celda.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
    // Crear y guardar muchos cursos
    public CursoEntidad crearCurso(Map<String, Object> datos){
        return cursoRepositorio.save(new CursoEntidad((int) datos.get("semestre"),
                                            (String) datos.get("nombre"),
                                            (boolean) datos.get("obligatorio"),
                                            (int) datos.get("creditos"),
                                            (String) datos.get("relacion"),
                                            (String) datos.get("tipo"),
                                            (int) datos.get("horasDeTrabajo"),
                                            (String) datos.get("areaDeFromacion"),
                                            (int) datos.get("maxEstudiantes"),
                                            new ArrayList<>()));
    }

    // Actualiza los datos de un usuario
    public void actulizarCurso(CursoEntidad curso, Map<String, Object> campos){
        campos.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CursoEntidad.class, key);
            if(field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, curso, value);
            }else{
                throw new RuntimeException("Campo no encontrado " + key);
            }
        });
        cursoRepositorio.save(curso);
    }

    public void eliminarCurso(Long idCurso) {
        if (cursoRepositorio.existsById(idCurso)) {
            cursoRepositorio.deleteById(idCurso); // Esto elimina el curso y sus estudiantes relacionados
        } else {
            throw new RuntimeException("El curso con ID " + idCurso + " no existe");
        }
    }

    public void eliminarCursoArchivo(Long idPlanEstudio){
        cursoRepositorio.deleteCursosByPlanEstudio(idPlanEstudio);
    }

    private static boolean esCombinada(Sheet hoja, Cell celda){
        for(int i = 0; i < hoja.getNumMergedRegions(); i++){
            CellRangeAddress combinada = hoja.getMergedRegion(i);
            if(combinada.isInRange(celda.getRowIndex(), celda.getColumnIndex())){
                return true;
            }
        }
        return false;
    }

    private String getVarlorCombinada(Sheet hoja, Cell celda){
        for(int i = 0; i < hoja.getNumMergedRegions(); i++){
            CellRangeAddress combinada = hoja.getMergedRegion(i);
            if(combinada.isInRange(celda.getRowIndex(), celda.getColumnIndex())){
                Row fila = hoja.getRow(combinada.getFirstRow());
                celda = fila.getCell(combinada.getFirstColumn());

                return (String) getCellValue(celda);
            }
        }
        return "";
    }

    public int semestre(String semestre){
        if(semestre.contains("Total")){
            return 0;
        }
        semestre = semestre.replace("Semestre ", "");

        int resultado = 0;

        for(int i = 0; i < semestre.length(); i++){
            int valorActual = numerosRomanos.get(semestre.charAt(i));
            int valorSiguiente = (i + 1 < semestre.length()) ? numerosRomanos.get(semestre.charAt(i+1)) : 0;

            if(valorActual < valorSiguiente){
                resultado -= valorActual;
            }else{
                resultado += valorActual;
            }
        }

        return resultado;
    }

}
