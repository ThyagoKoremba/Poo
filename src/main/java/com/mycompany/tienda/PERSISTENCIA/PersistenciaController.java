/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda.PERSISTENCIA;

import com.mycompany.tienda.LOGICA.Categoria;
import com.mycompany.tienda.LOGICA.Producto;
import com.mycompany.tienda.LOGICA.Cajero;
import com.mycompany.tienda.LOGICA.Venta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xthy
 */
public class PersistenciaController {
    CajeroJpaController cajJpa = new CajeroJpaController();
    CategoriaJpaController catJpa = new CategoriaJpaController();
    ProductoJpaController prodJpa = new ProductoJpaController();
    VentaJpaController ventJpa = new VentaJpaController();

    /*
    categorias
    */
    public void crearCategoria(Categoria cat) {
        catJpa.create(cat);          
    }
    public void eliminarCategoria(int id) throws Exception{
        catJpa.destroy(id);
    }
    public void editarCategoria(Categoria cat) throws Exception{
        catJpa.edit(cat);
    }
    public Categoria findCategoria(int id){
        return catJpa.findCategoria(id);
    }
    public ArrayList<Categoria> CategoriasAll(){
        List<Categoria> lista = catJpa.findCategoriaEntities();
        ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>(lista);
        return listaCategorias;
    }
    
    
    /*
    productos
    */
    public void crearProducto(Producto prod){
        prodJpa.create(prod);
    }
    public void eliminarProducto(Long id)throws Exception{
        prodJpa.destroy(id);
    }
    public void editarProducto(Producto prod) throws Exception{
        prodJpa.edit(prod);
    }
    public Producto findProducto(Long id){
        return prodJpa.findProducto(id);
    } 
    
    public ArrayList<Producto> ProductosAll(){
        List<Producto> lista = prodJpa.findProductoEntities();
        ArrayList<Producto> listaProductos = new ArrayList<Producto>(lista);
        return listaProductos;
    }
    
    public Cajero getCajeroPorUsuarioYPassword(String user, String contraseña) {
        return cajJpa.buscarPorUsuarioYPassword(user, contraseña);
    }

    public List<Producto> getProductosPorCategoria(Categoria categoria) {
            List<Producto>lista = prodJpa.getProductosPorCategoria(categoria);
            ArrayList<Producto> listaProductos=new ArrayList<Producto>(lista);
            return listaProductos;
    }
}
