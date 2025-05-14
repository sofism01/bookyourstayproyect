package org.uniquindio.application.domain;

import org.uniquindio.application.utils.Constantes;

import java.util.ArrayList;

public class Billetera {
    private String numero;
    private float saldo;
    private Cliente cliente;
    private ArrayList<Transaccion> transacciones;



    public Billetera(String numero, float saldo, Cliente cliente) {
        this.numero = numero;
        this.saldo = saldo;
        this.cliente = cliente;
        this.transacciones = new ArrayList<>();
    }

    public boolean tieneSaldo(float monto) {
        float montoConComision = monto + Constantes.COMISION;
        return saldo >= montoConComision;
    }

    public void retirar(float monto) throws Exception{

        float montoConComision = monto + Constantes.COMISION;

        if (montoConComision <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }


        saldo -= montoConComision;

    }

    public void depositar(float monto, Transaccion transaccion) throws Exception {

        if (monto <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        saldo += monto;
        transacciones.add(transaccion);
    }


    public ArrayList<Transaccion> obtenerTransacciones() {
        return transacciones;
    }

    public String getNumero() {
        return numero;
    }

    public float consultarSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "BilleteraVirtual{" +
                "numero='" + numero + '\'' +
                ", saldo=" + saldo +
                ", cliente=" + cliente +
                ", transacciones=" + transacciones +
                '}';
    }
}
