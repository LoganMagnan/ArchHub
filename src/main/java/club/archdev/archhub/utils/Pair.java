package club.archdev.archhub.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<K, V> {

	public K firstPair;
	public V secondPair;

	public Pair(K firstPair, V secondPair) {
		this.firstPair = firstPair;
		this.secondPair = secondPair;
	}
}
