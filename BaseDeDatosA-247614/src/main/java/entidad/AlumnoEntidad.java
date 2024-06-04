/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author caarl
 */
public class AlumnoEntidad {
    
    private int idAlumno;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private boolean eliminado;
    private boolean activo;
    
    public AlumnoEntidad(){}
    
    public AlumnoEntidad(int idAlumno,String nombres,String apellidoPaterno,String apellidoMaterno,Boolean eliminado,Boolean activo){
    this.idAlumno = idAlumno;
    this.nombres = nombres;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.eliminado = eliminado;
    this.activo = activo;
    }
    
    private AlumnoEntidad convertirAEntidad(ResultSet resultado) throws SQLException{
    int id = resultado.getInt("idAlumno");
    String nombre = resultado.getString("Nombres");
    String paterno = resultado.getString("apellidoPaterno");
    String materno = resultado.getString("apellidoMaterno");
    boolean eliminado = resultado.getBoolean("eliminado");
    boolean activo = resultado.getBoolean("activo");
    return new AlumnoEntidad(id, nombre,paterno, materno, eliminado, activo);
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "AlumnoEntidad{" + "idAlumno=" + idAlumno + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", eliminado=" + eliminado + ", activo=" + activo + '}';
    }
    
    
    
}
