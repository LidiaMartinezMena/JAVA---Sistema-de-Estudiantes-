package gm.datos;

import gm.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static gm.conexion.Conexion.getConexion;

public class EstudianteDAO {
    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql =  "SELECT * FROM estudiante ORDER BY id_estudiante";
            try {
                ps = con.prepareStatement(sql);
                //Guardamos el resultado en la variable rs
                rs = ps.executeQuery();
                //Como tendremos varias respuestas de la query, lo metemos en un bucle while
                while(rs.next()) {
                    var estudiante = new Estudiante();
                    estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApellido(rs.getString("apellido"));
                    estudiante.setEmail(rs.getString("email"));
                    estudiante.setTelefono(rs.getString("telefono"));
                    estudiantes.add(estudiante);
                }

            }catch (Exception e) {
                System.out.println("Ocurrió un error al mandar los datos: " + e.getMessage());
            }finally {
                    try{
                        con.close();
                    }catch (Exception e) {
                        System.out.println("Ocurrió un error al cerrar la conexión : " + e.getMessage());
                    }
                }
            return estudiantes;
    }

//Método buscar etsudiantes por ID:
public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, estudiante.getIdEstudiante());
                //Guardamos el resultado en la variable rs
                rs = ps.executeQuery();
                if(rs.next()){
                    estudiante.setNombre(rs.getString("nombre"));
                    estudiante.setApellido(rs.getString("apellido"));
                    estudiante.setEmail(rs.getString("email"));
                    estudiante.setTelefono(rs.getString("telefono"));
                    return true;
                }
            }catch (Exception e){
                System.out.println("Ocurrió un error al buscar estudiante: " + e.getMessage());
            } finally {
        try{
            con.close();
        }catch (Exception e) {
            System.out.println("Ocurrió un error al cerrar la conexión : " + e.getMessage());
        }
    }
    return false;
}

//Método agregar nuevo estudiante:
    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                "VALUES(?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Ocurrió un error al agregar estudiante: " + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrió un error al cerrar la conexión : " + e.getMessage());
            }
        }
        return false;
    }

    //Método modificar estudiante:

    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, email=? WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Ocurrió un error al modificar estudiante: " + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrió un error al cerrar la conexión : " + e.getMessage());
            }
        }
        return false;
    }

    //Método eliminar estudiante:

    public boolean eliminarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Ocurrió un error al eliminar estudiante: " + e.getMessage());
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrió un error al cerrar la conexión : " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {

    var estudianteDao = new EstudianteDAO();

    //Método agregar estudiantes:
    var nuevoEstudiante = new Estudiante("Carlos","Perez", "645235689", "Carlos.perez@correo.com" );
    var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
    if(agregado){
        System.out.println("Estudiante agregado: " + nuevoEstudiante);
    }else{
        System.out.println("No se agregó el estudiante:" + nuevoEstudiante);
    }

    //Método modificar un estudiante existente:
        var estudianteModificar = new Estudiante(1, "Juan Carlos", "Martinez", "562356253", "juan@correo.com");
        var modificado = estudianteDao.modificarEstudiante(estudianteModificar);
        if(modificado){
            System.out.println("Estudiante modificado: " + estudianteModificar);
        }else{
            System.out.println("No se modificó el estudiante:" + estudianteModificar);
        }

        // Método eliminar estudiante:
    var estudianteEliminado = new Estudiante(3);
        var eliminado = estudianteDao.eliminarEstudiante(estudianteEliminado);
        if(eliminado){
            System.out.println("Estudiante eliminado: " + estudianteEliminado);
        }else{
            System.out.println("No se eliminó el estudiante:" + estudianteEliminado);
        }

        //Método listar estudiantes
        System.out.println("Listado estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);



    //Método buscar por Id
        var estudiante1 = new Estudiante(2);
        System.out.println("Estudiante antes de la búsqueda: " + estudiante1);
        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
            if(encontrado){
                System.out.println("Estudiante encontrado: " + estudiante1);
            }else{
                System.out.println("No se encontró estudiante: " + estudiante1.getIdEstudiante());
            }



    }
}

