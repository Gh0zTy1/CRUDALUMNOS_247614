/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.AlumnoTablaDTO;
import dtos.EditarAlumnoTablaDTO;
import dtos.InsertarAlumnoTablaDTO;
import entidad.AlumnoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author caarl
 */
/**
 * Clase que implementa las operaciones de persistencia para la entidad Alumno.
 */
public class AlumnoDAO implements IAlumnoDAO {
    
    private IConexionBD conexionBD;

    
     // Constructor que inicializa el DAO con una conexión a la base de datos.
     
    public AlumnoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

  
     // Busca y retorna una lista de alumnos desde la base de datos.
     
    @Override
    public List<AlumnoEntidad> buscarAlumnosTabla() throws PersistenciaException {
        try {
            List<AlumnoEntidad> alumnosLista = null;

            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idAlumno, nombres, apellidoPaterno, apellidoMaterno, eliminado, activo FROM alumnos limit 5 offset 0";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            while (resultado.next()) {
                if (alumnosLista == null) {
                    alumnosLista = new ArrayList<>();
                }
                AlumnoEntidad alumno = this.convertirAEntidad(resultado);
                alumnosLista.add(alumno);
            }
            conexion.close();
            return alumnosLista;
        } catch (SQLException ex) {
            // hacer uso de Logger
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos");
        }
    }

    
     // Busca y retorna una lista de alumnos desde la base de datos con paginación.
    
    @Override
    public List<AlumnoEntidad> buscarAlumnosTablaSig(int i) throws PersistenciaException {
        try {
            List<AlumnoEntidad> alumnosLista = null;

            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idAlumno, nombres, apellidoPaterno, apellidoMaterno, eliminado, activo FROM alumnos limit 5 offset ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setInt(1, i);
            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                if (alumnosLista == null) {
                    alumnosLista = new ArrayList<>();
                }
                AlumnoEntidad alumno = this.convertirAEntidad(resultado);
                alumnosLista.add(alumno);
            }
            conexion.close();
            return alumnosLista;
        } catch (SQLException ex) {
            // hacer uso de Logger
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos");
        }
    }

    
     // Convierte un ResultSet en una entidad AlumnoEntidad.
     
    private AlumnoEntidad convertirAEntidad(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idAlumno");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        boolean eliminado = resultado.getBoolean("eliminado");
        boolean activo = resultado.getBoolean("activo");
        return new AlumnoEntidad(id, nombre, paterno, materno, eliminado, activo);
    }

    
     // Inserta un nuevo alumno en la base de datos y retorna la lista actualizada de alumnos.
     
    @Override

public List<AlumnoEntidad> insertarAlumnosTabla(InsertarAlumnoTablaDTO guardarAlumno) throws PersistenciaException {
    Connection conexion = null;
    try {
        conexion = this.conexionBD.crearConexion();
        conexion.setAutoCommit(false); // Desactivar autocommit para manejar la transacción manualmente

        String codigoSQL = "INSERT INTO alumnos (nombres, apellidoPaterno, apellidoMaterno, eliminado, activo) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
        preparedStatement.setString(1, guardarAlumno.getNombres());
        preparedStatement.setString(2, guardarAlumno.getApellidoPaterno());
        preparedStatement.setString(3, guardarAlumno.getApellidoMaterno());
        preparedStatement.setInt(4, guardarAlumno.getEliminado());
        preparedStatement.setInt(5, guardarAlumno.getActivo());
        preparedStatement.execute();

        conexion.commit(); // Confirmar la transacción
        return buscarAlumnosTabla();
    } catch (SQLException ex) {
        if (conexion != null) {
            try {
                conexion.rollback(); // Deshacer la transacción en caso de error
            } catch (SQLException rollbackEx) {
                System.out.println("Error al realizar rollback: " + rollbackEx.getMessage());
                throw new PersistenciaException("Ocurrió un error al realizar rollback de la transacción");
            }
        }
        System.out.println(ex.getMessage());
        throw new PersistenciaException("Ocurrió un error al insertar en la base de datos");
    } finally {
        if (conexion != null) {
            try {
                conexion.setAutoCommit(true); // Restaurar el autocommit al valor predeterminado
                conexion.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}


    
     // Edita un alumno en la base de datos y retorna la lista actualizada de alumnos.
     
    @Override
    public List<AlumnoEntidad> editarAlumnosTabla(EditarAlumnoTablaDTO alumno) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "UPDATE alumnos SET nombres = ?, apellidoPaterno = ?, apellidoMaterno = ?, eliminado = ?, activo = ? WHERE idAlumno = ?;";
            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setString(1, alumno.getNombres());
            preparedStatement.setString(2, alumno.getApellidoPaterno());
            preparedStatement.setString(3, alumno.getApellidoMaterno());
            preparedStatement.setInt(4, alumno.getEliminado());
            preparedStatement.setInt(5, alumno.getActivo());
            preparedStatement.setInt(6, alumno.getIdAlumno());
            preparedStatement.execute();
            conexion.close();
            return buscarAlumnosTabla();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al editar en la base de datos");
        }
    }

 
     // Elimina un alumno de la base de datos y retorna la lista actualizada de alumnos.

    @Override
    public List<AlumnoEntidad> eliminarAlumnosTabla(int val1) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "DELETE FROM alumnos WHERE idAlumno = ?;";
            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setInt(1, val1);
            preparedStatement.execute();
            conexion.close();
            return buscarAlumnosTabla();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al eliminar el dato de la base de datos");
        }
    }
}
