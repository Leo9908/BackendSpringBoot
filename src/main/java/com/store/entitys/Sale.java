package com.store.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private Date date;

    /**
     * Pongo la relación del lado Sale y no del lado Order porque no todas las
     * ordenes
     * se venden aunque se acepten.
     * Cuando vaya a hacer los reportes debo buscar las ventas no las ordenes
     * porque con las ventas se que orden fue la que se vendío y puedo sacar los
     * datos correctos para el reporte
     * y tiene más sentido porque es un reporte de ventas no de pedidos
     */
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
