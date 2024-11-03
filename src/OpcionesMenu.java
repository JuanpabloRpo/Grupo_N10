import javax.swing.*;

public class OpcionesMenu {

    private Metodos metodos = new Metodos();

    public String[][] opcionRegistrar(String[] codigos, String[] modelosCarros, String[] yearsF, String[] precios, int contador) {

        // Se vefifica que se pueda registrar mas vehiculos
        if (contador > 9) {
            JOptionPane.showMessageDialog(null, "Ya no se pueden registrar mas vehiculos.");
            return null;
        }

        // Se crea un arreglo provicional para recibir los datos de los arreglos paralelos
        // Se llama al metodo registrarVehiculo que esta en la clase Metodos
        String[] datos = metodos.registrarVehiculo(codigos);

        // Cada indice de datos corresponde a un dato de un arreglo diferente
        // Se utiliza contador para saber de cual vehiculo estamos hablando
        // Se agrega los datos del vehiculo a los arreglos paralelos
        codigos[contador] = datos[0];
        modelosCarros[contador] = datos[1];
        yearsF[contador] = datos[2];
        precios[contador] = datos[3];

        String[][] provicional = new String[4][codigos.length];
        provicional[0] = codigos;
        provicional[1] = modelosCarros;
        provicional[2] = yearsF;
        provicional[3] = precios;


        return provicional;
    }

    public String[][] opcionActualizar(String[][] inventario, String[] codigos) {

        String codigoVehiculo = JOptionPane.showInputDialog("Ingrese el codigo de verificación del vehiculo");

        if (!metodos.isIn(codigos, codigoVehiculo)) {
            JOptionPane.showMessageDialog(null, "el codigo no se encontró");
            return inventario;
        }

        String dia = JOptionPane.showInputDialog("Ingrese el dia de la semana, el numero del dia o el nombre del dia: ");
        while (!verificarDia(dia)) {
            dia = JOptionPane.showInputDialog("ingrese el dia nuevamente: ");
        }

        inventario = metodos.actualizarEstadoDisponibilidad(inventario, indiceSegunCodigo(codigos, codigoVehiculo), combertirDiaToNumber(dia) - 1);

        return inventario;
    }

    public String[][] opcionConsultarDisponibilidad(String[] codigos, String[][] inventario) {

        String codigoVehiculo = JOptionPane.showInputDialog("Ingrese el codigo de verificación del vehiculo");

        if (!metodos.isIn(codigos, codigoVehiculo)) {
            JOptionPane.showMessageDialog(null, "El codigo no se encontró");
            return null;
        }

        String[][] dias = new String[2][7];

        for (int i = 0; i < codigos.length; i++) {

            if (codigos[i] == null) {
                continue;
            }
            if (codigos[i].equals(codigoVehiculo)) {
                dias = metodos.consultarDisponibilidadVehiculo(inventario, i);
                break;
            }

        }
        return dias;


    }

    public int[][] opcionConsultarInventario(String[][] inventario){

        String dia = JOptionPane.showInputDialog("Ingrese el dia de la semana, el numero del dia o el nombre del dia: ");
        while (!verificarDia(dia)) {
            dia = JOptionPane.showInputDialog("ingrese el dia nuevamente: ");
        }

        return metodos.consultarInventarioDia(inventario,combertirDiaToNumber(dia)-1);

    }

    public void opcionConsultarIngresosPotenciales(String[][] inventario, int[] precios){


        int totalIngresosPotenciales = 0;
        for (int i = 0; i < inventario.length; i++) {

            totalIngresosPotenciales+=metodos.calcularIngresosPotenciales(inventario[i], precios, i);
        }

        JOptionPane.showMessageDialog(null,"Suponiendo que se venden todos los vehiculos que estan Disponibles en la semana en este momento, el total de ingresos seria: "+totalIngresosPotenciales);
    }

    public int indiceSegunCodigo(String[] codigosVehiculos, String codigo){

        int indice=0;
        for (int i = 0; i < codigosVehiculos.length; i++) {
            if (codigosVehiculos[i].equals(codigo)){
                indice=i;
                break;
            }
        }

        return indice;
    }

    public boolean verificarDia(String dia ){

        switch (dia.toLowerCase()){
            case "1":
            case "lunes":
            case "2":
            case "martes":
            case "3":
            case "miercoles":
            case "4":
            case "jueves":
            case "5":
            case "viernes":
            case "6":
            case "sabado":
            case "7":
            case "domingo":
                return true;
            default:
                JOptionPane.showMessageDialog(null,"El dia "+ dia + " No es valido");
                return false;

        }
    }

    public int combertirDiaToNumber(String dia){
        switch (dia.toLowerCase()){
            case "lunes":
                return 1;
            case "martes":
                return 2;
            case "miercoles":
                return 3;
            case "jueves":
                return 4;
            case "viernes":
                return 5;
            case "sabado":
                return 6;
            case "domingo":
                return 7;
            default:
                return Integer.parseInt(dia);

        }
    }


}
