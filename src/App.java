import java.util.Scanner; 
/* Esto es una aplicación de gestión de información de empleados donde se interactua con la base de datos.
 */

public class App {
    static Scanner lector = new Scanner (System.in);
    public static void main(String[] args) throws Exception {
        menuPrincipal();

    }
    public static void menuPrincipal (){
        /*Este método muestra el menú con las acciones que podemos realizar */
        String opcionLeida;
        int opcion = -1;
        boolean error = true; 
        
        do {
            try {
                System.out.println("-- Menú principal --");
                System.out.println("1. Contratar personal");
                System.out.println("2. Consultar lista empleados");
                System.out.println("3. Cambiar nombre de un empleado");
                System.out.println("4. Cambiar apellido de un empleado");
                System.out.println("5. Trasladar puesto de trabajo de un empleado");
                System.out.println("6. Buscar nombre de un empleado");
                System.out.println("7. Buscar puesto de trabajo de un empleado");
                System.out.println("8. Salir");
                System.out.println("Escriba una de las opciones anteriores: ");
                opcionLeida = lector.nextLine();
                opcion = Integer.parseInt(opcionLeida);
                if (opcion >= 1 && opcion <= 8){
                    error = false;
                } else {
                    error = true;
                    System.out.println("No existe esa opción");
                }
            } catch (Exception e){
                error = true;
                System.out.println("Valor no válido");
            }
        switch (opcion) {
            case 1:
                gestorBaseDatos.contratarEmpleado();
                break;
            case 2:
                gestorBaseDatos.mostrarListaEmpleados();
                break;
            case 3:
                gestorBaseDatos.cambiarNombre();
                break;
            case 4:
                gestorBaseDatos.cambiarApellido();
                break;
            case 5:
                gestorBaseDatos.cambiarPuesto();

            case 6:
                gestorBaseDatos.mostrarNombreApellido();
                break;
            case 7:
                gestorBaseDatos.mostrarPuesto();
                break;
            case 8:
            System.out.println("Hasta pronto");
                break;
        }
    } while(error || opcion != 8);
        lector.close();
    }  
}
