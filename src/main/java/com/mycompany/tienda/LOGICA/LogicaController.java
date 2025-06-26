/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.LOGICA;

import com.mycompany.tienda.PERSISTENCIA.PersistenciaController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xthy
 */
public class LogicaController {
    public class GeneradorRemito {

    public static void generarRemito(Venta venta, String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("********** REMITO DE VENTA **********");
            writer.newLine();
            writer.write("Fecha: " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            writer.newLine();
            writer.write("ID Venta: " + venta.getId());
            writer.newLine();
            writer.write("-------------------------------");
            writer.newLine();

            for (VentaProductos vp : venta.getProductos()) {
                String linea = String.format("%-20s Cant: %d  Precio Unit: $%.2f  Subtotal: $%.2f",
                        vp.getProducto().getNombre(),
                        vp.getCantidad(),
                        vp.getProducto().getPrecio(),
                        vp.getSubtotal());
                writer.write(linea);
                writer.newLine();
            }

            writer.write("-------------------------------");
            writer.newLine();
            writer.write(String.format("TOTAL: $%.2f", venta.getTotal()));
            writer.newLine();
            writer.write("***********************************");
            writer.newLine();
        }
    }
}
public static class ProductoCantidad {
    private Producto producto;
    private int cantidad;

    public ProductoCantidad(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    public Producto getProducto() {
        return producto;
    }
    public int getCantidad() {
        return cantidad;
    }
}
    PersistenciaController persContr = new PersistenciaController();

    public Cajero autenticar(String user, String contraseña) {
        return persContr.getCajeroPorUsuarioYPassword(user, contraseña);
    }

    /*
    categoria
     */
    public void crearCategoria(Categoria cat) {
        persContr.crearCategoria(cat);
    }

    public void eliminarCategoria(int id) {
        try {
            persContr.eliminarCategoria(id);
            System.out.println("Categoría eliminada correctamente.");
        } catch (javax.persistence.RollbackException ex) {
            System.out.println("No se puede eliminar la categoría porque tiene productos relacionados.");
        } catch (Exception e) {
            System.out.println("Ocurrio un error al eliminar la categoria: " + e.getMessage());
        }

    }

    public void editCategoria(Categoria cat) throws Exception {
        persContr.editarCategoria(cat);
    }

    public Categoria findCategoria(int id) {
        return persContr.findCategoria(id);
    }

    public ArrayList<Categoria> CategoriasAll() {
        return persContr.CategoriasAll();
    }

    /*
    producto    
     */
    public List<Producto> traerProductosDeCategoria(Categoria categoria) {
        return persContr.getProductosPorCategoria(categoria);
    }

    public void crearProducto(Producto prod) {
        persContr.crearProducto(prod);
    }

    public void eliminarProducto(Long id) {
        try {
            persContr.eliminarProducto(id);
            System.out.println("Producto eliminado correctamente.");
        } catch (Exception e) {
            System.out.println("Ocurrio un error al eliminar el Producto: " + e.getMessage());
        }
    }

    public void editProducto(Producto prod) throws Exception {
        persContr.editarProducto(prod);
    }

    public Producto findProducto(Long id) {
        return persContr.findProducto(id);
    }

    public ArrayList<Producto> ProductosAll() {
        return persContr.ProductosAll();
    }
public void crearVenta(List<ProductoCantidad> productosCantidad) throws Exception {
    Venta venta = new Venta();
    venta.setFecha(LocalDate.now());

    List<VentaProductos> listaVentaProductos = new ArrayList<>();

    for (ProductoCantidad pc : productosCantidad) {
        Producto producto = findProducto(pc.getProducto().getId());

        if (producto == null) {
            throw new Exception("Producto no encontrado: " + pc.getProducto().getNombre());
        }

        if (producto.getStock() < pc.getCantidad()) {
            throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - pc.getCantidad());
        editProducto(producto);  // Actualizar stock en BD

        VentaProductoId id = new VentaProductoId();
        id.setProductoId(producto.getId());

        VentaProductos ventaProducto = new VentaProductos();
        ventaProducto.setProducto(producto);
        ventaProducto.setCantidad(pc.getCantidad());
        ventaProducto.setId(id);
        ventaProducto.setVenta(venta);

        listaVentaProductos.add(ventaProducto);
    }

    venta.setProductos(listaVentaProductos);

    // Guardar la venta en la base de datos
    persContr.guardarVenta(venta);

    // Crear carpeta remitos si no existe
    File carpetaRemitos = new File("remitos");
    if (!carpetaRemitos.exists()) {
        carpetaRemitos.mkdirs();
    }

    // Generar el archivo TXT tipo remito
    try {
        String rutaArchivo = "remitos/remito_" + venta.getId() + ".txt";
        GeneradorRemito.generarRemito(venta, rutaArchivo);
        System.out.println("Remito generado en: " + rutaArchivo);
    } catch (IOException e) {
        System.err.println("Error generando remito: " + e.getMessage());
    }
}
  

}
