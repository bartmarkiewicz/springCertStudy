package rewards.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.orm.ObjectRetrievalFailureException;

import rewards.Dining;
import rewards.internal.account.Account;
import rewards.internal.restaurant.BenefitAvailabilityPolicy;
import rewards.internal.restaurant.Restaurant;
import rewards.internal.restaurant.RestaurantRepository;

import common.money.Percentage;

/**
 * A dummy restaurant repository implementation. Has a single restaurant "Apple Bees" with a 8% benefit availability
 * percentage that's always available.
 * 
 * Stubs facilitate unit testing. An object needing a RestaurantRepository can work with this stub and not have to bring
 * in expensive and/or complex dependencies such as a Database. Simple unit tests can then verify object behavior by
 * considering the state of this stub.
 */
public class StubRestaurantRepository implements RestaurantRepository {

	private Map<String, Restaurant> restaurantsByMerchantNumber = new HashMap<String, Restaurant>();

	public StubRestaurantRepository() {
		Restaurant restaurant = new Restaurant("1234567890", "Apple Bees");
		restaurant.setBenefitPercentage(Percentage.valueOf("8%"));
		restaurant.setBenefitAvailabilityPolicy(new AlwaysReturnsTrue());
		restaurantsByMerchantNumber.put(restaurant.getNumber(), restaurant);
	}

	public Restaurant findByNumber(String merchantNumber) {
		Restaurant restaurant = (Restaurant) restaurantsByMerchantNumber.get(merchantNumber);
		if (restaurant == null) {
			throw new ObjectRetrievalFailureException(Restaurant.class, merchantNumber);
		}
		return restaurant;
	}

	@Override
	public <S extends Restaurant> S save(S entity) {
		return null;
	}

	@Override
	public <S extends Restaurant> Iterable<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	public Optional<Restaurant> findById(Long aLong) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long aLong) {
		return false;
	}

	@Override
	public Iterable<Restaurant> findAll() {
		return null;
	}

	@Override
	public Iterable<Restaurant> findAllById(Iterable<Long> longs) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Long aLong) {

	}

	@Override
	public void delete(Restaurant entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {

	}

	@Override
	public void deleteAll(Iterable<? extends Restaurant> entities) {

	}

	@Override
	public void deleteAll() {

	}

	/**
	 * A simple "dummy" benefit availability policy that always returns true. Only useful for testing--a real
	 * availability policy might consider many factors such as the day of week of the dining, or the account's reward
	 * history for the current month.
	 */
	private static class AlwaysReturnsTrue implements BenefitAvailabilityPolicy {
		public boolean isBenefitAvailableFor(Account account, Dining dining) {
			return true;
		}
	}
}