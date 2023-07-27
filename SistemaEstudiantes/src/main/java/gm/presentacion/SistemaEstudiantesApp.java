package gm.presentacion;

import gm.datos.EstudianteDAO;
import gm.dominio.Estudiante;

import java.util.Scanner;

public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        var salir = false;
        var consola = new Scanner(System.in);
        var estudianteDao = new EstudianteDAO();
        while (!salir) {
           try {
               mostrarMenu();
               salir = ejecutarOpciones(consola, estudianteDao);
           } catch (Exception e){
               System.out.println("Ocurrió un error al ejecutar la operación: " + e.getMessage());
           }
            System.out.println();
        }
    }

    //Método mostrar menú
    public static void mostrarMenu(){
        System.out.println("""
                ***Sistema de estudiantes ***
                1. Listar estudiantes
                2. Buscar estudiante
                3. Agregar estudiante
                4. Modificar estudiante
                5. Eliminar estudiante
                6. Salir
                Elige una opción:
                """);
    }

    //Método ejecutar opciones:
    private static boolean ejecutarOpciones(Scanner consola, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;
        switch (opcion){
                case 1 -> {
                    System.out.println("Listado de Estudiantes...");
                    var estudiantes = estudianteDAO.listarEstudiantes();
                    estudiantes.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println("Introduce el ID del estudiante:");
                    var idEstudiante = Integer.parseInt(consola.nextLine());
                    var estudiante = new Estudiante(idEstudiante);
                    var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                    if(encontrado){
                        System.out.println("Estudiante encontrado: " + estudiante);
                    }else {
                        System.out.println("Estudiante no encontrado: " + estudiante);
                    }
                }
                case 3 ->{
                    System.out.println("Agregar estudiante:");
                    System.out.print("Nombre: ");
                    var nombre = consola.nextLine();
                    System.out.print("Apellido: ");
                    var apellido = consola.nextLine();
                    System.out.print("Telefono: ");
                    var telefono = consola.nextLine();
                    System.out.print("Email: ");
                    var email = consola.nextLine();
                    //Crear objeto estudiante:
                    var estudiante = new Estudiante(nombre, apellido, telefono, email);
                    var agregado = estudianteDAO.agregarEstudiante(estudiante);
                        if(agregado){
                            System.out.println("Estudiante agregado: " + estudiante);
                        }else {
                            System.out.println("Estudiante no agregado: " + estudiante);
                        }
                }
                case 4 ->{
                    System.out.println("Modificar estudiante:");
                    System.out.println("Id Estudiante: ");
                    var idEstudiante = Integer.parseInt(consola.nextLine());
                    1System.out.print("Nombre: ");
                    var nombre = consola.nextLine();
                    System.out.print("Apellido: ");
                    var apellido = consola.nextLine();
                    System.out.print("Telefono: ");
                    var telefono = consola.nextLine();
                    System.out.print("Email: ");
                    var email = consola.nextLine();
                    //Crear objeto estudiante a modificar:
                    var estudiante = new Estudiante(idEstudiante, nombre, apellido, telefono, email);
                    var modificado = estudianteDAO.modificarEstudiante(estudiante);
                    if(modificado){
                        System.out.println("Estudiante modificado: " + estudiante);
                    }else {
                        System.out.println("Estudiante no modificado: " + estudiante);
                    }
                }
                case 5 ->{
                    System.out.println("Eliminar estudiante: ");
                    System.out.println("Id Estudiante: ");
                    var idEstudiante = Integer.parseInt(consola.nextLine());
                    var estudiante = new Estudiante(idEstudiante);
                    var eliminado = estudianteDAO.eliminarEstudiante(estudiante);
                        if(eliminado){
                            System.out.println("Estudiante eliminado: " + estudiante);
                        }else {
                            System.out.println("Estudiante no eliminado: " + estudiante);
                        }
                }
                case 6 -> {
                    System.out.println("Hasta pronto!");
                    salir = true;
                }
                default -> System.out.println("Opción no reconocida");
            }
            return salir;
        }
    }
