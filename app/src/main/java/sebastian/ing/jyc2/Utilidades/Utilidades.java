package sebastian.ing.jyc2.Utilidades;

/**
 * Created by Usuario on 14/02/2019.
 */

public class Utilidades
{
    //CONSTANTES BASE DE DATOS
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jyc.db";

    //Constantes tabla Bodega
    public static final String TABLA_BODEGA="bodega";
    public static final String CAMPO_ID_PRO2="id_pro2";
    public static final String CAMPO_CANTIDAD_BOD="cantidad_bod";
    public static final String CAMPO_ID_BOD="id_bod";
    public static final String CREAR_TABLA_BODEGA=
            "CREATE TABLE "+TABLA_BODEGA+" ("+CAMPO_ID_PRO2+" INTEGER, "+CAMPO_CANTIDAD_BOD+" INTEGER, "+CAMPO_ID_BOD+" INTEGER)";
    //Constantes tabla Cliente_Int
    public static final String TABLA_CLIENTE="cliente";
    public static final String CAMPO_ID_CLIE="id_clie";
    public static final String CAMPO_NOM_RAZON_SOCIAL="nom_razon_social";
    public static final String CAMPO_NOMBRE_CLIE="nombre_clie";
    public static final String CAMPO_APELLIDO_CLIE="apellido_clie";
    public static final String CAMPO_DIRECCION_CLIE="direccion_clie";
    public static final String CAMPO_TELEFONO_CLIE="telefono_clie";
    public static final String CAMPO_CORREO_CLIE ="correo_clie";
    public static final String CAMPO_DIA_ATENCION_CLIE="dia_atencion_clie";
    public static final String CAMPO_NUM_RUTA_CLIE="num_ruta_clie";
    public static final String CREAR_TABLA_CLIENTE=
            "CREATE TABLE "+TABLA_CLIENTE+" ("+CAMPO_ID_CLIE+" INTEGER, "+CAMPO_NOM_RAZON_SOCIAL+" TEXT, "+CAMPO_NOMBRE_CLIE+" TEXT, "+CAMPO_APELLIDO_CLIE+" TEXT, "+CAMPO_DIRECCION_CLIE+" TEXT, "+CAMPO_TELEFONO_CLIE+" TEXT, "+CAMPO_CORREO_CLIE+" TEXT, "+CAMPO_DIA_ATENCION_CLIE+" TEXT, "+CAMPO_NUM_RUTA_CLIE+" INTEGER)";
    //Constantes tabla Factura
    public static final String TABLA_FACTURA="factura";
    public static final String CAMPO_ID_PRO3="id_pro3";
    public static final String CAMPO_ID_FACTURA="id_factura";
    public static final String CAMPO_ID_VEN1="id_ven1";
    public static final String CAMPO_ID_CLIE1="id_clie1";
    public static final String CAMPO_CANTIDAD_FAC="cantidad_fac";
    public static final String CAMPO_FECHA_FAC="fecha_fac";
    public static final String CREAR_TABLA_FACTURA=
            "CREATE TABLE "+TABLA_FACTURA+" ("+CAMPO_ID_PRO3+" INTEGER, "+CAMPO_ID_FACTURA+" INTEGER, "+CAMPO_ID_VEN1+" INTEGER, "+CAMPO_ID_CLIE1+" INTEGER, "+CAMPO_CANTIDAD_FAC+" INTEGER, "+CAMPO_FECHA_FAC+" TEXT)";
    //Constantes tabla Producto
    public static final String TABLA_PRODUCTO="producto";
    public static final String CAMPO_ID_PRO="id_pro";
    public static final String CAMPO_NOMBRE_PRO="nombre_pro";
    public static final String CAMPO_PRECIO_PRO="precio_pro";
    public static final String CAMPO_LINEA_PRO="linea_pro";
    public static final String CAMPO_CASA_EXPORTACION_PRO="casa_exportacion_pro";
    public static final String CREAR_TABLA_PRODUCTO=
            "CREATE TABLE "+TABLA_PRODUCTO+" ("+CAMPO_ID_PRO+" INTEGER, "+CAMPO_NOMBRE_PRO+" TEXT, "+CAMPO_PRECIO_PRO+" INTEGER, "+CAMPO_LINEA_PRO+" TEXT, "+CAMPO_CASA_EXPORTACION_PRO+" TEXT)";
    //Constantes tabla Vendedor
    public static final String TABLA_VENDEDOR="vendedor";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_APELLIDO="apellido";
    public static final String CAMPO_RUTA="ruta";
    public static final String CAMPO_TELEFONO="telefono";
    public static final String CAMPO_CORREO="correo";
    public static final String CREAR_TABLA_VENDEDOR=
            "CREATE TABLE "+TABLA_VENDEDOR+" ("+CAMPO_ID+" INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_APELLIDO+" TEXT, "+CAMPO_RUTA+" TEXT, "+CAMPO_TELEFONO+" TEXT, "+CAMPO_CORREO+" TEXT)";

    //Base de datos provicional
    public static final String TABLA_CLIENTE_PROVICIONAL="cliente_provicional";
    public static final String CAMPO_ID_CLIE_PROVICIONAL="id_clie_provicional";
    public static final String CAMPO_NOM_RAZON_SOCIAL_PROVICIONAL="nom_razon_social_provicional";
    public static final String CAMPO_NOMBRE_CLIE_PROVICIONAL="nombre_clie_provicional";
    public static final String CAMPO_APELLIDO_CLIE_PROVICIONAL="apellido_clie_provicional";
    public static final String CAMPO_DIRECCION_CLIE_PROVICIONAL="direccion_clie_provicional";
    public static final String CAMPO_TELEFONO_CLIE_PROVICIONAL="telefono_clie_provicional";
    public static final String CAMPO_CORREO_CLIE_PROVICIONAL ="correo_clie_provicional";
    public static final String CAMPO_DIA_ATENCION_CLIE_PROVICIONAL="dia_atencion_clie_provicional";
    public static final String CAMPO_NUM_RUTA_CLIE_PROVICIONAL="num_ruta_clie_provicional";
    public static final String CREAR_TABLA_CLIENTE_PROVICIONAL=
            "CREATE TABLE "+TABLA_CLIENTE_PROVICIONAL+" ("+CAMPO_ID_CLIE_PROVICIONAL+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOM_RAZON_SOCIAL_PROVICIONAL+" TEXT, "+CAMPO_NOMBRE_CLIE_PROVICIONAL+" TEXT, "+CAMPO_APELLIDO_CLIE_PROVICIONAL+" TEXT, "+CAMPO_DIRECCION_CLIE_PROVICIONAL+" TEXT, "+CAMPO_TELEFONO_CLIE_PROVICIONAL+" TEXT, "+CAMPO_CORREO_CLIE_PROVICIONAL+" TEXT, "+CAMPO_DIA_ATENCION_CLIE_PROVICIONAL+" TEXT, "+CAMPO_NUM_RUTA_CLIE_PROVICIONAL+" INTEGER)";

    //Constantes tabla Registro
    public static final String TABLA_REGISTRO="registro";
    public static final String CAMPO_ID_REGISTRO="id_registro";
    public static final String CAMPO_ID_CLIENTE_REGISTRO="id_cliente_registro";
    public static final String CAMPO_ID_VENDEDOR_REGISTRO="id_vendedor_registro";
    public static final String CREAR_TABLA_REGISTRO=
            "CREATE TABLE "+TABLA_REGISTRO+" ("+CAMPO_ID_REGISTRO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_ID_CLIENTE_REGISTRO+" INTEGER, "+CAMPO_ID_VENDEDOR_REGISTRO+" INTEGER)";
}
