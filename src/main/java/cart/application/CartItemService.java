package cart.application;

import cart.domain.CartItem;
import cart.domain.Member;
import cart.repository.CartItemRepository;
import cart.repository.ProductRepository;
import cart.ui.dto.request.CartItemQuantityUpdateRequest;
import cart.ui.dto.request.CartItemRequest;
import cart.ui.dto.response.CartItemResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartItemService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartItemService(final ProductRepository productRepository, final CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItemResponse> findByMember(final Member member) {
        return cartItemRepository.findAllByMemberId(member.getId())
                .stream()
                .map(CartItemResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(final Member member, final CartItemRequest cartItemRequest) {
        return cartItemRepository.save(
                new CartItem(member, productRepository.findById(cartItemRequest.getProductId())));
    }

    @Transactional
    public void updateQuantity(final Member member, final Long id, final CartItemQuantityUpdateRequest request) {
        final CartItem cartItem = cartItemRepository.findById(id);
        cartItem.checkOwner(member);

        if (request.getQuantity() == 0) {
            cartItemRepository.deleteById(id);
            return;
        }

        cartItem.changeQuantity(request.getQuantity());
        cartItemRepository.updateQuantity(cartItem);
    }

    @Transactional
    public void deleteById(final Member member, final Long id) {
        final CartItem cartItem = cartItemRepository.findById(id);
        cartItem.checkOwner(member);

        cartItemRepository.deleteById(id);
    }
}
