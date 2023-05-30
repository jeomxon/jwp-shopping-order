package cart.domain.value;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @DisplayName("가격이 0보다 작으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -10_000})
    void validateRange(int input) {
        // when, then
        assertThatThrownBy(() -> new Price(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0보다 작을 수 없습니다.");
    }
}
