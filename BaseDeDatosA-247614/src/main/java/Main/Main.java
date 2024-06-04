/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;


import negocio.AlumnoNegocio;
import negocio.IAlumnoNegocio;
import persistencia.AlumnoDAO;
import persistencia.ConexionBD;
import persistencia.IAlumnoDAO;
import persistencia.IConexionBD;
import presentacion.FrmCRUD;


/**
 *
 * @author caarl
 */
public class Main {

    public static void main(String[] args) {

        IConexionBD conexionBD = new ConexionBD();
        IAlumnoDAO alumnoDAO =  new AlumnoDAO(conexionBD);
        
        IAlumnoNegocio alumnoNegocio = new AlumnoNegocio(alumnoDAO);
        
        FrmCRUD frmcrud = new  FrmCRUD (alumnoNegocio);
        frmcrud.show();
        
        System.out.println("Termina la ejecución");
    }
        
}

