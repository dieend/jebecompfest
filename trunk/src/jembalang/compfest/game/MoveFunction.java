package jembalang.compfest.game;

public abstract class MoveFunction {
	public abstract float getdx(float t);
	public abstract float getdy(float t);
	public static final int STAY = 0;
	public static final int LINEAR = 1;
	private static float[] c;
	public static MoveFunction Factory(int type, float...f) {
		MoveFunction.c = f;
		if (type == LINEAR){
			return new MoveFunction() {
				
				@Override
				public float getdy(float t) {
					return c[0];
				}
				
				@Override
				public float getdx(float t) {
					return 0;
				}
			};
		} else {
			return new MoveFunction() {
				
				@Override
				public float getdy(float t) {
					return 0;
				}
				
				@Override
				public float getdx(float t) {
					return 0;
				}
			};
		}
	}
}
