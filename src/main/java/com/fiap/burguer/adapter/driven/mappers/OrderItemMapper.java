package com.fiap.burguer.adapter.driven.mappers;

import com.fiap.burguer.adapter.driven.entities.ClientEntity;
import com.fiap.burguer.adapter.driven.entities.OrderEntity;
import com.fiap.burguer.adapter.driven.entities.OrderItemEntity;
import com.fiap.burguer.core.domain.Client;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        BeanUtils.copyProperties(orderItem, orderItemEntity);

        return orderItemEntity;
    }

    public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();

        BeanUtils.copyProperties(orderItemEntity, orderItem);


        return orderItem;
    }

    public static List<OrderItem> toDomain(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(OrderItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static List<OrderItemEntity> toEntity(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemMapper::toEntity)
                .collect(Collectors.toList());
    }



    public static OrderItemEntity toEntity(Order order, OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderItem, orderItemEntity);
        BeanUtils.copyProperties(order, orderEntity);

        orderItemEntity.setOrder(orderEntity);
        return orderItemEntity;
    }

    public static OrderItem toDomain(OrderEntity orderEntity, OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();
        Order order = new Order();

        BeanUtils.copyProperties(orderItemEntity, orderItem);
        BeanUtils.copyProperties(orderEntity, order);

        orderItem.setOrder(order);
        return orderItem;
    }

    public static List<OrderItem> toDomain(OrderEntity orderEntity) {
        return orderEntity.getOrderItemsList().stream()
                .map(orderItemEnity -> OrderItemMapper.toDomain(orderEntity, orderItemEnity))
                .collect(Collectors.toList());
    }

    public static List<OrderItemEntity> toEntity(Order order) {
        return order.getOrderItemsList().stream()
                .map( orderItem -> OrderItemMapper.toEntity(order, orderItem))
                .collect(Collectors.toList());
    }

}
