package database;

public class SequenceSingleton {

	private static volatile SequenceSingleton ss = null;
	
	private static volatile Double d = null;
	
	private SequenceSingleton() {}
	
	public static SequenceSingleton getInstance() {
		if(ss == null) {
			ss = new SequenceSingleton();
			d = 0.;
		}
		return ss;
	}
	
	public Double getId() {
		d += 1;
		return d;
	}
}
