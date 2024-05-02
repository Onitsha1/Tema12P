import java.sql.*;

public class gestorBaseDatos {
/*Esta clase contiene todas las acciones que la aplicación puede realizar */


    private static Connection getConnection() throws SQLException {
        /*Este método realiza la conexión al servidor. Más adelante lo introduciremos a una variable 
         * para poder trabajar de una manera más cómoda.
         */
        
        String url = "jdbc:mysql://localhost:3306/empresa_ejemplo";
        String usuario = "root";
        String contrasenia = "ContraseñaSQL";
        return DriverManager.getConnection(url, usuario, contrasenia);
    }

    public static String pedirCodigoEmpleado(){
        /*Con el objetivo de no repetir líneas de código, se crea este método para 
         * poder utilizar el valor que devuelve como variable en otros métodos.
         */
        String codigoEmpleado;
        System.out.println("Escriba el codigo del empleado: ");
        codigoEmpleado = App.lector.nextLine();
        return codigoEmpleado;
    }
    public static String pedirNombre(){
        String nombreIntroducido;
        
        System.out.println("Escriba el nombre del empleado: ");
        nombreIntroducido = App.lector.nextLine();
        return nombreIntroducido;
    }

    public static String pedirApellido(){
        String apellidoIntroducido;
        System.out.println("Escriba el apellido del empleado: ");
        apellidoIntroducido = App.lector.nextLine();
        return apellidoIntroducido;
    }
    public static String pedirPuesto(){
        String puestoIntroducido;
        System.out.println("Escriba el puesto de trabajo del empleado: ");
        puestoIntroducido = App.lector.nextLine();
        return puestoIntroducido;
    }


    public static void contratarEmpleado() {
        /* */
        String nombre = pedirNombre();
        String apellido = pedirApellido();
        String puesto = pedirPuesto();
        String codigoEmpleado = pedirCodigoEmpleado();
        Connection con = null; /*Se declara esta variable como null para que sea accesible por el catch porque la inicializaremos más adelante
        en el bloque try, sino el compilador no sería capaz de encontrar ninguna referencia a esta variable */
          
        try {
            con = getConnection(); // Como dijimos, guardamos el establecimiento de conexión en una variable.
            PreparedStatement statement = con.prepareStatement("INSERT INTO empleados (codigo_empleado, nombre, apellido, puesto) VALUES (?, ?, ?, ?)");
            /* Esta instrucción SQL agrega en la tabla empleados en las columnas que se indican, los valores de las 4 variables que se muestran
            más adelante */
            con.setAutoCommit(false); // Esto hace que no se haga un commit automáticamente.
            /*El primer parámetro de lo que se muestra a continuación indica en qué orden se insertan los valores siendo el primer "?" en la instrucción
             * el argumento 1 y el segundo "?" el argumento 2, y así sucesivamente, además del valor en el segundo parámetro.*/
            statement.setString(1, codigoEmpleado); 
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setString(4, puesto);
            statement.executeUpdate(); //Se ejecuta la instrucción
            System.out.println("Empleado agregado correctamente.");
            con.commit(); //Si no salta ninguna Excepción SQL, se guardarán los datos en el base de datos.
            con.close(); // Se cierra la conexión a la base de datos.
        } catch (SQLException e) {
            System.err.println("Error al agregar empleado: " + e.getMessage()); //Nos mostrará este mensaje junto con el error que nos indique la base de datos.
            try {
                con.rollback(); //Si  se produce una excepción, los datos introduccidos se revierten y se vuelve al estado de antes de introducir los datos.
                } catch (SQLException el){ /* El método rollback() necesita un manejo de excepción SQL y al estar fuera del try no se puede
                    beneficiar del catch que hay arriba suya, por lo tanto debemos establecer un bloque try-catch para este método.*/
                }
        }   
    }


    public static void cambiarNombre() {

        String codigoEmpleado = pedirCodigoEmpleado();
        String nombreActual = pedirNombre(); //Pide el nombre como doble verificación para evitar cambios indeseados.
        System.out.println("Escriba el nuevo nombre: ");
        String nombreNuevo = App.lector.nextLine();
        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);
            /*  Esta instrucción cambia el valor nombre en la tabla empleados de los empleados cuyo código y nombre coincidan con los que 
            se introducen*/
            PreparedStatement statement = con.prepareStatement("UPDATE empleados SET nombre = ? WHERE codigo_empleado = ? AND nombre = ?");
            statement.setString(1, nombreNuevo);
            statement.setString(2,codigoEmpleado);
            statement.setString(3, nombreActual);
            int nombresCambiados = statement.executeUpdate();
            System.out.println("Se ha cambiado " + nombresCambiados + " nombre correctamente.");
            con.commit();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al cambiar el nombre: " + e.getMessage());
            try{ 
                con.rollback();
            } catch (SQLException el){
            }
        }
    }

    public static void cambiarApellido(){

        String codigoEmpleado = pedirCodigoEmpleado();
        String apellidoActual = pedirApellido();
        System.out.println("Escriba el nuevo apellido: ");
        String apellidoNuevo = App.lector.nextLine();
        Connection con = null;

        try {con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("UPDATE empleados SET apellido = ? WHERE codigo_empleado = ? AND apellido = ?");
            statement.setString(1, apellidoNuevo);
            statement.setString(2,codigoEmpleado);
            statement.setString(3, apellidoActual);
            int apellidosCambiados = statement.executeUpdate();
            System.out.println("Se ha cambiado " + apellidosCambiados + " apellido correctamente.");
            con.commit();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al cambiar el apellido: " + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException el) {
            }
        }
    }


    public static void cambiarPuesto() {

        String codigoEmpleado = pedirCodigoEmpleado();
        String puestoActual = pedirPuesto();
        System.out.println("Escriba el nuevo puesto de trabajo: ");
        String puestoNuevo = App.lector.nextLine();
        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement("UPDATE empleados SET puesto = ? WHERE codigo_empleado = ? AND puesto = ?");
            statement.setString(1, puestoNuevo);
            statement.setString(2,codigoEmpleado);
            statement.setString(3, puestoActual);
            int puestosTrasladados = statement.executeUpdate();
            System.out.println("Se ha trasladado " + puestosTrasladados + " empleado a otro departamento.");
            con.close();
            con.commit();
        } catch (SQLException e) {
            System.err.println("Error al trasladar el puesto de trabajo: " + e.getMessage());
            try{
                con.rollback();
            } catch (SQLException el){
            }
        }
    }

    public static void mostrarListaEmpleados(){
        /*Este método muestra una lista de empleados para facilitar la búsqueda de información al actor
         dado que en esta app se opera con código de empleado y puede ser difícil tenerlos siempre a mano.*/
        try (Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM empleados")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Nombre completo: " + resultSet.getString("nombre") + " " + resultSet.getString("apellido") + ", Puesto: " + resultSet.getString("puesto") + ", Código: " + resultSet.getString("codigo_empleado"));
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta: " + e.getMessage());
        }
    }

    public static void mostrarNombreApellido(){
        /*Este método muestra el nombre y apellido de un empleado a partir de su código de empleado. */
        
        String codigoEmpleado = pedirCodigoEmpleado();

        try (Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT nombre, apellido FROM empleados WHERE codigo_empleado = ?")) {
            statement.setString(1, codigoEmpleado);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Nombre completo: " + resultSet.getString("nombre") + " " + resultSet.getString("apellido"));
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta: " + e.getMessage());
        }
    }
    
    public static void mostrarPuesto(){
        // Este método muestra el puesto de trabajo que ocupa un empleado a partir de su código de empleado.
        String codigoEmpleado = pedirCodigoEmpleado();

        try (Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT puesto FROM empleados WHERE codigo_empleado = ?")) {
            statement.setString(1, codigoEmpleado);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Puesto de trabajo: " + resultSet.getString("puesto"));
            }
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta: " + e.getMessage());
        }
    }
}
