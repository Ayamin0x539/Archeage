
public class RegradeLevel {

	private String name; // for example, heroic
	private float success;
	private float greatSuccess;
	private float fail;
	private float downgrade;
	private String down;
	private String up;
	private String upTwo;
	private String downTwo;
	private String downThree;
	
	private RegradeLevel(String name, float success, float greatSuccess, float fail, float downgrade, String down, String up, String upTwo, String downTwo, String downThree) {
		this.name = name;
		this.success = success;
		this.greatSuccess = greatSuccess;
		this.fail = fail;
		this.downgrade = downgrade;
		this.down = down;
		this.up = up;
		this.upTwo = upTwo;
		this.downTwo = downTwo;
		this.downThree = downThree;
	}
	
	public static class Builder {
		private String name;
		private float success;
		private float greatSuccess;
		private float fail;
		private float downgrade;
		private String down;
		private String up;
		private String upTwo;
		private String downTwo;
		private String downThree;
		
		public Builder name(String name) { this.name = name; return this; }
		public Builder success(float success) { this.success = success/100; return this; }
		public Builder greatSuccess(float greatSuccess) { this.greatSuccess = greatSuccess/100; return this; }
		public Builder fail(float fail) { this.fail = fail/100; return this; }
		public Builder downgrade(float downgrade) { this.downgrade = downgrade/100; return this; }
		public Builder down(String down) { this.down = down; return this; }
		public Builder up(String up) { this.up = up; return this; }
		public Builder upTwo(String upTwo) { this.upTwo = upTwo; return this; }
		public Builder downTwo(String downTwo) { this.downTwo = downTwo; return this; }
		public Builder downThree(String downThree) { this.downThree = downThree; return this; }
		
		public RegradeLevel build() {
			return new RegradeLevel(name, success, greatSuccess, fail, downgrade, down, up, upTwo, downTwo, downThree);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public float getSuccess() {
		return success;
	}
	
	public float getGreatSuccess() {
		return greatSuccess;
	}
	
	public float getFail() {
		return fail;
	}
	
	public float getDowngrade() {
		return downgrade;
	}
	
	public String up() {
		return up;
	}
	
	public String upTwo() {
		return upTwo;
	}
	
	public String down() {
		return down;
	}

	public String downTwo() {
		return downTwo;
	}
	
	public String downThree() {
		return downThree;
	}
}
