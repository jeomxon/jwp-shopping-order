package cart.repository;

import cart.dao.OrderDao;
import cart.dao.OrderItemDao;
import cart.domain.Member;
import cart.domain.Order;
import cart.domain.OrderItem;
import cart.entity.OrderEntity;
import cart.entity.OrderItemEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    public OrderRepository(final OrderDao orderDao, final OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    public Order saveOrder(final Order order) {
        final OrderEntity orderEntity = orderDao.save(new OrderEntity(
                order.getId(),
                order.calculateTotalItemPrice(),
                order.calculateDiscountedTotalItemPrice(),
                order.getShippingFee(),
                order.getOrderedAt(),
                order.getMember().getId())
        );
        final List<OrderItemEntity> orderItemEntities = getOrderItemEntities(order, orderEntity);
        orderItemDao.batchSave(orderItemEntities);
        return new Order(
                orderEntity.getId(),
                order.getOrderItems(),
                order.getShippingFee(),
                order.getOrderedAt(),
                order.getMember()
        );
    }

    public List<Order> findAllByMember(final Member member) {
        final List<OrderEntity> orderEntities = orderDao.findAllByMemberId(member.getId());
        final List<Order> orders = new ArrayList<>();
        for (final OrderEntity orderEntity : orderEntities) {
            final List<OrderItem> orderItems = orderItemDao.findAllByOrderId(orderEntity.getId())
                    .stream()
                    .map(orderItemEntity -> new OrderItem(
                                    orderItemEntity.getId(),
                                    orderItemEntity.getName(),
                                    orderItemEntity.getPrice(),
                                    orderItemEntity.getImageUrl(),
                                    orderItemEntity.getQuantity(),
                                    orderItemEntity.getDiscountRate()
                            )
                    ).collect(Collectors.toList());
            final Order order = new Order(
                    orderEntity.getId(),
                    orderItems,
                    orderEntity.getShippingFee(),
                    orderEntity.getOrderedAt(),
                    member
            );
            orders.add(order);
        }
        return orders;
    }

    public Order findOrderById(final Member member, final Long orderId, final List<OrderItem> orderItems) {
        final OrderEntity orderEntity = orderDao.findById(orderId);
        return new Order(
                orderEntity.getId(),
                orderItems,
                orderEntity.getShippingFee(),
                orderEntity.getOrderedAt(),
                member
        );
    }

    public List<OrderItem> findAllOrderItemsByOrderId(final Long orderId) {
        return orderItemDao.findAllOrderItemsByOrderId(orderId)
                .stream()
                .map(orderItemEntity -> new OrderItem(
                        orderItemEntity.getId(),
                        orderItemEntity.getName(),
                        orderItemEntity.getPrice(),
                        orderItemEntity.getImageUrl(),
                        orderItemEntity.getQuantity(),
                        orderItemEntity.getDiscountRate()
                )).collect(Collectors.toList());
    }

    private List<OrderItemEntity> getOrderItemEntities(final Order order, final OrderEntity orderEntity) {
        return order.getOrderItems()
                .stream()
                .map(orderItem -> new OrderItemEntity(
                        orderItem.getId(),
                        orderItem.getName(),
                        orderItem.getPrice(),
                        orderItem.getImageUrl(),
                        orderItem.getQuantity(),
                        orderItem.getDiscountRate(),
                        orderEntity.getId())
                ).collect(Collectors.toList());
    }
}
