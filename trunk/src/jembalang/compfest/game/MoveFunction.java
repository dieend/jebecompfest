package jembalang.compfest.game;

public abstract class MoveFunction {
	public abstract float getdx();
	public abstract float getdy();
	public static final int STAY = 0;
	public static final int LINEAR = 1;
	private static float[] c;
	public static MoveFunction Factory(int type, float...f) {
		MoveFunction.c = f;
		if (type == LINEAR){
			return new MoveFunction() {
				
				@Override
				public float getdy() {
					return c[0];
				}
				
				@Override
				public float getdx() {
					return 0;
				}
			};
		} else {
			return new MoveFunction() {
				
				@Override
				public float getdy() {
					return 0;
				}
				
				@Override
				public float getdx() {
					return 0;
				}
			};
		}
	}
}
