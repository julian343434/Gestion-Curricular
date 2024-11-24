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
        numerosRomanos.put('x', 10);
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

    // Crear y guardar muchos cursos
    public List<CursoEntidad> crearCursoArchivo(Sheet hoja){
        List<CursoEntidad> cursos = new ArrayList<>();

        Map<String, Object> datos = new HashMap<>();

        for (int i = 4; i <= hoja.getLastRowNum(); i++){
            Row fila = hoja.getRow(i);

            if(fila == null){
                continue;
            }
            if(esCombinada(hoja, fila.getCell(0))){
                datos.put("semestre", semestre(getVarlorCombinada(hoja, fila.getCell(0))));
            }else{
                if(fila.getCell(0).getStringCellValue().contains("Total")){
                    continue;
                }
                datos.put("nombre", fila.getCell(0).getStringCellValue());
                
                if(fila.getCell(1).getStringCellValue().trim().isEmpty()){
                    datos.put("obligatorio", false);
                }else{
                    datos.put("obligatorio", true);
                }
                datos.put("cretidos", fila.getCell(3).getNumericCellValue());
                datos.put("relacion", fila.getCell(4).getStringCellValue());
                datos.put("tipo", fila.getCell(5).getStringCellValue());
                datos.put("horasDeTrabajo", fila.getCell(6).getNumericCellValue());
                if(!fila.getCell(9).getStringCellValue().trim().isEmpty()){
                    datos.put("areaDeFormacion", "Basica");
                }else if (!fila.getCell(10).getStringCellValue().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Especifica");
                }else if (!fila.getCell(11).getStringCellValue().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Investigacion");
                }else if (!fila.getCell(12).getStringCellValue().trim().isEmpty()) {
                    datos.put("areaDeFormacion", "Complementaria");
                }
                datos.put("maxEstudiantes", fila.getCell(13));

                cursos.add(cursoRepositorio.save(new CursoEntidad((int) datos.get("semestre"),
                                            (String) datos.get("nombre"),
                                            (boolean) datos.get("obligatorio"),
                                            (int) datos.get("creditos"),
                                            (String) datos.get("relacion"),
                                            (String) datos.get("tipo"),
                                            (int) datos.get("horasDeTrabajo"),
                                            (String) datos.get("areaDeFromacion"),
                                            (int) datos.get("maxEstudiantes"),
                                            new ArrayList<>())));
            }

        }

        return cursos;
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
    public void actulizarUsuario(CursoEntidad curso, Map<String, Object> campos){
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

    private static boolean esCombinada(Sheet hoja, Cell celda){
        for(int i = 0; i < hoja.getNumMergedRegions(); i++){
            CellRangeAddress combinada = hoja.getMergedRegion(i);
            if(combinada.isInRange(celda.getRowIndex(), celda.getColumnIndex())){
                return true;
            }
        }
        return false;
    }

    private static String getVarlorCombinada(Sheet hoja, Cell celda){
        for(int i = 0; i < hoja.getNumMergedRegions(); i++){
            CellRangeAddress combinada = hoja.getMergedRegion(i);
            if(combinada.isInRange(celda.getRowIndex(), celda.getColumnIndex())){
                Row fila = hoja.getRow(combinada.getFirstRow());
                celda = fila.getCell(combinada.getFirstColumn());

                return celda.getStringCellValue();
            }
        }
        return "";
    }

    public int semestre(String semestre){
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
