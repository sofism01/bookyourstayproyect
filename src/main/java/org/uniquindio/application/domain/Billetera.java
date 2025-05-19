package org.uniquindio.application.domain;

import lombok.Getter;
import lombok.Setter;
import org.uniquindio.application.utils.Constantes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Billetera implements Serializable {
    private String numero;
    private float saldo;
    private Cliente cliente;
    private ArrayList<Transaccion> transacciones;



    public Billetera(Cliente cliente) {
        this.numero = UUID.randomUUID().toString();
        this.saldo = 0;
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

    public void depositar(float monto) throws Exception {

        if (monto <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        saldo += monto;

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
