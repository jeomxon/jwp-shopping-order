package cart.ui.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderShowResponse {

    private List<OrderItemDto> orderItems;
    private LocalDateTime orderedAt;
    private int totalItemDiscountAmount;
    private int totalMemberDiscountAmount;
    private int totalItemPrice;
    private int discountedTotalItemPrice;
    private int shippingFee;
    private int totalPrice;

    public OrderShowResponse(
            final List<OrderItemDto> orderItems,
            final LocalDateTime orderedAt,
            final int totalItemDiscountAmount,
            final int totalMemberDiscountAmount,
            final int totalItemPrice,
            final int discountedTotalItemPrice,
            final int shippingFee,
            final int totalPrice
    ) {
        this.orderItems = orderItems;
        this.orderedAt = orderedAt;
        this.totalItemDiscountAmount = totalItemDiscountAmount;
        this.totalMemberDiscountAmount = totalMemberDiscountAmount;
        this.totalItemPrice = totalItemPrice;
        this.discountedTotalItemPrice = discountedTotalItemPrice;
        this.shippingFee = shippingFee;
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public int getTotalItemDiscountAmount() {
        return totalItemDiscountAmount;
    }

    public int getTotalMemberDiscountAmount() {
        return totalMemberDiscountAmount;
    }

    public int getTotalItemPrice() {
        return totalItemPrice;
    }

    public int getDiscountedTotalItemPrice() {
        return discountedTotalItemPrice;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
