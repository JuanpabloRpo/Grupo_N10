import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Decoración
        String lineas="---------------------------------------------------------------";

        // Variables para almacenar el inventario de disponible y para almacenar los vehiculos
        String[][] Inventario= new String[10][7];

        String[] CodigosIdentificacion = new String[10];
        String[] modelos = new String[10];
        int[] years = new int[10];
        int[] preciosVenta = new int[10];

        OpcionesMenu opcionesMenu=new OpcionesMenu();

        // Control para el ciclo while
        boolean flagMenu=true;

        // Contador para la cantidad de vehiculos que ingresa
        int count=0;

        while (flagMenu){

            // Se crea un menu con opciones para que el usuario elija
            int opcionMenu = Integer.parseInt(JOptionPane.showInputDialog("\n------------------------- AutoPassion -------------------------\n"+
                    "Digite 1 para Registrar un vehiculo\n" +
                    "Digite 2 para Actualizar el estado de disponibilidad\n"+
                    "Digite 3 para Consultar disponibilidad de un vehiculo\n"+
                    "Digite 4 para Consultar inventario de un dia\n"+
                    "Digite 5 para listar inventario semanal\n"+
                    "Digite 6 para Consultar ingresos potenciales\n"+
                    "Digite 7 para salir del programa\n"+
                    lineas));

            // Se maneja las opciones del menu con un switch case
            switch (opcionMenu){
                case 1:

                    // se crea una matriz provicional para almacenar los arreglos actualizdos con los nuevos datos
                    String[][] provicional = opcionesMenu.opcionRegistrar(CodigosIdentificacion,modelos,convertirListaToStringI(years),convertirListaToStringI(preciosVenta),count);
                    if (provicional==null){
                        break;
                    }

                    CodigosIdentificacion = provicional[0];
                    modelos = provicional[1];
                    years = convertirListaToInteger(provicional[2]);
                    preciosVenta = convertirListaToInteger(provicional[3]);

                    Inventario = agregarDisponibilidad(Inventario,count);

                    // Se actualiza la cantidad de vehiculos registrados
                    count++;

                    JOptionPane.showMessageDialog(null,"Registro hecho");
                    break;
                case 2:

                    Inventario = opcionesMenu.opcionActualizar(Inventario,CodigosIdentificacion);


                    break;
                case 3:

                    String[][] dias = opcionesMenu.opcionConsultarDisponibilidad(CodigosIdentificacion,Inventario);
                    if (dias==null){
                        break;
                    }

                    mostrarDiasDisponibles(dias[0]);
                    mostrarDiasNoDisponibles(dias[1]);

                    break;
                case 4:

                     int [][] indices = opcionesMenu.opcionConsultarInventario(Inventario);

                     mostrarVehiculosDisponibles(indices[0],CodigosIdentificacion,modelos,years,preciosVenta,indices[2][0]);
                     mostrarVehiculosNoDisponibles(indices[1],CodigosIdentificacion,modelos,years,preciosVenta,indices[2][0]);

                    break;
                case 5:
                    mostrarListarInventario(Inventario,CodigosIdentificacion,modelos,years,preciosVenta);
                    break;
                case 6:
                    opcionesMenu.opcionConsultarIngresosPotenciales(Inventario,preciosVenta);
                    break;
                case 7:
                    // Se rompe con el ciclo While con la variable flagmenu que contiene al menu
                    JOptionPane.showMessageDialog(null,"Gracias por estar en AutoPassion, Le esperamos nuevamente pronto.");
                    flagMenu=false;
                    break;
                default:
                    // Encaso tal de no seleccionar una opcion del menú no realiza una operación, muetra un mensaje y regresa al menú
                    JOptionPane.showMessageDialog(null,lineas+"\n"+"la opción "+opcionMenu+" NO es una opción.\n"+lineas);
            }

        }
    }

    public static String[][] agregarDisponibilidad(String[][] inventario,int vehiculo){

        Metodos metodos=new Metodos();
        // Se agrega la disponibilidad del vehiculo; debido a que apenas se registra, tiene todos los dias de la semana disponible
        // Se recorre con el for solamente los dias de la semana y con el vehiculo(count que nos dice el vehiculo que tenemos seleccionado) seleccionamos la fila del vehiculo que estamos registrando
        for (int i=0; i< inventario[0].length;i++){

            int opcion = Integer.parseInt(JOptionPane.showInputDialog("Digitemos la disponibilidad del vehiculo para el dia "+metodos.combertirNumberToDia(i+1) + ":\n Digite 1 para Disponible o 2 para Vendido"));

            while (true){
                if (opcion==1){
                    inventario[vehiculo][i]="Disponible";
                    break;
                } else if (opcion==2) {
                    inventario[vehiculo][i]="Vendido";
                    break;
                }else {
                    opcion = Integer.parseInt(JOptionPane.showInputDialog("Opcion invalida:"+ "\n Digite 1 para Disponible o 2 para Vendido"));
                }
            }


        }
        return inventario;
    }

    public static int[] convertirListaToInteger(String[] lista){

        int[] listaInteger = new int[lista.length];
        for (int i=0;i<lista.length;i++){
            if (lista[i] != null){
                listaInteger[i]=Integer.parseInt(lista[i]);
            }

        }
        return listaInteger;
    }

    public static String[] convertirListaToStringI(int[] lista){

        String[] listaString = new String[lista.length];
        for (int i=0;i<lista.length;i++){
            listaString[i] = String.valueOf(lista[i]);
        }
        return listaString;

    }

    public static void mostrarDiasDisponibles(String[] diasDisponibles){
        String mensaje = "Los dias disponibles son: \n";
        for (int i = 0; i < diasDisponibles.length; i++) {
            if (diasDisponibles[i]!=null){
                mensaje+=diasDisponibles[i]+"\n";
            }
        }

        JOptionPane.showMessageDialog(null,mensaje);
    }

    public static void mostrarDiasNoDisponibles(String[] diasNoDisponibles){
        String mensaje = "Los dias No disponibles son: \n";
        for (int i = 0; i < diasNoDisponibles.length; i++) {
            if (diasNoDisponibles[i]!=null){
                mensaje+=diasNoDisponibles[i]+"\n";
            }
        }

        JOptionPane.showMessageDialog(null,mensaje);
    }

    public static void mostrarVehiculosDisponibles(int[] indices, String[] codigos, String[] modelos, int[] year, int[] precios,int dia){
        Metodos metodos = new Metodos();
        String lineas="---------------------------------------------------------------";
        String mensaje = "Los vehiculos disponibles para el dia "+metodos.combertirNumberToDia(dia)+ " son: \n";
        boolean doble=true;
        int carros = 0;

        for (int i = 0; i < indices.length; i++) {
            //
            if (indices[i]==0 && i>0 || indices[i]<0){

                break;
            }
            if (i>4 && doble){
                mensaje += "\n(1/2)\n";
                JOptionPane.showMessageDialog(null,mensaje);
                mensaje = "Los vehiculos disponibles para el dia "+metodos.combertirNumberToDia(dia)+ " son: \n";
                doble=false;
            }
                mensaje+=lineas+"\n";
                mensaje+="Vehiculo modelo: "+modelos[indices[i]]+"\n";
                mensaje+="Con codigo de identificación: "+ codigos[indices[i]] +"\n";
                mensaje+="Fabricado en el año: "+year[indices[i]]+"\n";
                mensaje+="con un precio de: "+precios[indices[i]]+"\n";
                carros++;
        }
        if(!doble){
            mensaje += "\n(2/2)\n";
        }
        if (carros!=0){
            JOptionPane.showMessageDialog(null,mensaje);
        }

    }

    public static void mostrarVehiculosNoDisponibles(int[] indices, String[] codigos, String[] modelos, int[] year, int[] precios,int dia){
        Metodos metodos = new Metodos();
        String lineas="---------------------------------------------------------------";
        String mensaje = "Los vehiculos NO disponibles para el dia "+metodos.combertirNumberToDia(dia)+ " son: \n";
        int carros = 0;

        boolean doble=true;

        for (int i = 0; i < indices.length; i++) {
            if (indices[i]==0 && i>0 || indices[i]<0){
                break;
            }

            if (i>4 && doble){
                mensaje += "\n(1/2)\n";
                JOptionPane.showMessageDialog(null,mensaje);
                mensaje = "Los vehiculos disponibles para el dia "+metodos.combertirNumberToDia(dia)+ " son: \n";
                doble=false;
            }

            mensaje+=lineas+"\n";
            mensaje+="Vehiculo modelo: "+modelos[indices[i]]+"\n";
            mensaje+="Con codigo de identificación: "+ codigos[indices[i]] +"\n";
            mensaje+="Fabricado en el año: "+year[indices[i]]+"\n";
            mensaje+="con un precio de: "+precios[indices[i]]+"\n";
            carros++;
        }

        if(!doble){
            mensaje += "\n(2/2)\n";
        }
        if(carros!=0){
            JOptionPane.showMessageDialog(null,mensaje);
        }

    }

    public static void mostrarListarInventario(String[][] inventario,String[] codigos, String[] modelos, int[] year, int[] precios){

        Metodos metodos=new Metodos();

        String lineas="---------------------------------------------------------------";
        String mensaje;

        int contarDisponibles = 0;
        int contarVehiculso = 0;


        if (inventario[0][0]!=null){


            // Recorremos los dias de la semana
            for (int i = 0; i < inventario[0].length; i++) {

                boolean doble=true;
                mensaje = metodos.combertirNumberToDia(i+1).toUpperCase()+": \n";


                for (int j = 0; j < inventario.length; j++) {
                    if (inventario[j][0]==null){
                        break;
                    }

                    if (j>4 && doble){
                        mensaje += "\n(1/2)\n";
                        JOptionPane.showMessageDialog(null,mensaje);
                        mensaje = metodos.combertirNumberToDia(i+1).toUpperCase()+": \n";
                        doble=false;

                    }

                    contarVehiculso++;
                    mensaje+="Vehiculo modelo: "+modelos[j]+"\n";
                    mensaje+="Con codigo de identificación: "+ codigos[j] +"\n";
                    mensaje+="Fabricado en el año: "+year[j]+"\n";
                    mensaje+="con un precio de: "+precios[j]+"\n";
                    mensaje+="Disponibilidad: "+inventario[j][i]+"\n";



                    if(inventario[j][i].equalsIgnoreCase("Disponible")){
                        contarDisponibles++;
                    }

                    mensaje+=lineas+"\n";


                    
                }
                if(doble==false){
                    mensaje += "\n(2/2)\n";
                }
                JOptionPane.showMessageDialog(null,mensaje);

            }
        }else {
            JOptionPane.showMessageDialog(null,"No hay datos ingresados para mostrar.");
        }


        if(contarVehiculso>0){

            int noDisponibles=contarVehiculso-contarDisponibles;

            double porcentajeVendidos= ((double) noDisponibles/contarVehiculso)*100;
            double porcentajeDisponibles= ((double) contarDisponibles/contarVehiculso)*100;
            mensaje = "En la semana se vendieron: "+noDisponibles+" vehiculos, eso es el  "+String.format("%.2f", porcentajeVendidos)+"% de "+contarVehiculso+ " \n "+
                    "y aun siguen disponibles: "+contarDisponibles+" vehiculos, eso es el  "+String.format("%.2f", porcentajeDisponibles)+"% de "+contarVehiculso;
            JOptionPane.showMessageDialog(null,mensaje);
        }
    }

}