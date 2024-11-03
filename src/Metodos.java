import javax.swing.*;

public class Metodos {

    // Se crea constructor vacio(no necesario en este caso)
    public Metodos() {
    }

    // Metodo que retorna un arreglo con los datos para los areglos paralelos
    public String[] registrarVehiculo(String[] codigos){

        String[] datos = new String[4];

        // Se agregan los datos correspondientes a cada indice de datos
        String codigoIdentificacion =  JOptionPane.showInputDialog("Ingrese el código de identificación: ");

        // Se utiliza el metodo isIn para saber si un codigo esta duplicado
        // El ciclo while se mantiene activo mientras isIn sea == true
        while (isIn(codigos,codigoIdentificacion) || codigoIdentificacion.isEmpty()){
            codigoIdentificacion = JOptionPane.showInputDialog("El codigo de identificación: "+codigoIdentificacion+" ya se encuentra registrado, ingrese otro codigo: ");
        }

        // Se agregan los datos correspondientes a cada indice de datos
        datos[0]=codigoIdentificacion;
        datos[1]=JOptionPane.showInputDialog("Ingrese el modelo del vehiculo: ");

        int year=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año de fabricación: "));

        // Se verifica que el año de fabricación no sea menor a el año de invencion del vehiculo y que no sea mayor al año actual
        while ((year<1886) || (year > 2024)){
            year=Integer.parseInt(JOptionPane.showInputDialog("El año de fabricación del vehiculo no puede ser: "+ year +", Ingrese el año nuevamente: "));
        }

        // Se agregan los datos correspondientes a cada indice de datos
        datos[2]=String.valueOf(year);
        datos[3] = JOptionPane.showInputDialog("Ingrese el precio del vehiculo");

        return datos;
    }

    public String[][] actualizarEstadoDisponibilidad(String[][] inventario, int vehiculo, int dia){
        String estado = inventario[vehiculo][dia];

        if (estado.equals("Disponible")){

            inventario[vehiculo][dia] = "Vendido";
            JOptionPane.showMessageDialog(null,"Cambio hecho de: Disponible a Vendido");

        }else {

            inventario[vehiculo][dia] = "Disponible";
            JOptionPane.showMessageDialog(null,"Cambio hecho de: Vendido a Disponible");
        }

        return inventario;
    }

    public String[][] consultarDisponibilidadVehiculo(String[][] inventario, int vehiculo){

        String[][] dias = new String[2][7];


        int indiceDisponible = 0;
        int indiceNoDisponible = 0;

        for (int i = 0; i < inventario[0].length; i++) {
            if (inventario[vehiculo][i].equalsIgnoreCase("Disponible")){
                dias[0][indiceDisponible] = combertirNumberToDia(i+1);
                indiceDisponible++;
            }else {
                dias[1][indiceNoDisponible] = combertirNumberToDia(i+1);
                indiceNoDisponible++;
            }
        }

        return dias;
    }

    public int[][] consultarInventarioDia(String[][] inventario, int dia){

        int[][] indices = new int[3][10];

        indices[0][0] = -1;
        indices[1][0] =-1;

        int indiceDisponible = 0;
        int indiceNoDisponible = 0;

        for (int i = 0; i <inventario.length ; i++) {

            if (inventario[i][dia]==null){
                break;
            }
            if (inventario[i][dia].equalsIgnoreCase("Disponible")){

                indices[0][indiceDisponible] = i;
                indiceDisponible++;

            }else {

                indices[1][indiceNoDisponible] = i;
                indiceNoDisponible++;
            }
        }
        indices[2][0]=dia+1;

        return  indices;
    }

    public int calcularIngresosPotenciales(String[] inventario, int[] precios, int indice){

        int sumIngresoCarroDisponible = 0;

        for (int i = 0; i < inventario.length; i++) {
            if (inventario[i]==null){
                break;
            }
            if (inventario[i].equalsIgnoreCase("Disponible")){
                sumIngresoCarroDisponible+=precios[indice];
            }
        }


        return sumIngresoCarroDisponible;
    }

    public boolean isIn(String[] codigos, String codigo){

        for (int i=0;i<codigos.length;i++){
            if (codigos[i]==null){
                continue;
            }
            if(codigos[i].equals(codigo)){
                return true;
            }

        }
        return false;
    }

    public String combertirNumberToDia(int dia){
        switch (dia){
            case 1:
                return "lunes";
            case 2:
                return "martes";
            case 3:
                return "miercoles";
            case 4:
                return "jueves";
            case 5:
                return "viernes";
            case 6:
                return "sabado";
            case 7:
                return "domingo";
            default:
                return null;
        }
    }

}
