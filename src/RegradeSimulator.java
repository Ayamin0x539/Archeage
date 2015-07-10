import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

/**
 * @author cse
 * 
 * Simulates regrading in the MMORPG Archeage.
 * Will run regrades from _Rare_ to _Celestial_.
 * The reason behind this is because there is no breakage chance.
 * Downgrades are assumed to be distributed uniformly.
 * That is, if we proc a downgrade, it has a 33.33...% chance to go down one stage, two stages, or three stages. All the same chance.
 * The method averageNumberOfRegrades(int) will return the expected number of regrades.
 * 
 * Pass in some huge numbers and let the Law of Large Numbers show you the way!
 */
public class RegradeSimulator {

	private Hashtable<String, RegradeLevel> regradeLevels;
	
	public static final String START_REGRADE_LEVEL = "unique";
	
	private RegradeLevel grand;
	private RegradeLevel rare;
	private RegradeLevel arcane;
	private RegradeLevel heroic;
	private RegradeLevel unique;
	private RegradeLevel celestial;
	
	private String currentRegradeLevel;
	
	public String getCurrentRegradeLevel() {
		return currentRegradeLevel;
	}
	public RegradeSimulator() {
		grand = new RegradeLevel.Builder().name("grand").success(48).greatSuccess(12).fail(40).downgrade(0).up("rare").upTwo("arcane").build();
		rare = new RegradeLevel.Builder().name("rare").success(36).greatSuccess(9).fail(55).downgrade(0).up("arcane").upTwo("heroic").down("grand").build();
		arcane = new RegradeLevel.Builder().name("arcane").success(42).greatSuccess((float)10.5).fail((float)47.5).downgrade(0).up("heroic").upTwo("unique").down("rare").build();
		heroic = new RegradeLevel.Builder().name("heroic").success(42).greatSuccess(11).fail(29).downgrade(19).up("unique").upTwo("celestial").down("arcane").downTwo("rare").downThree("grand").build();
		unique = new RegradeLevel.Builder().name("unique").success(40).greatSuccess(10).fail(30).downgrade(20).up("celestial").upTwo("divine").down("heroic").downTwo("arcane").downThree("rare").build();
		
		regradeLevels = new Hashtable<String, RegradeLevel>();
		regradeLevels.put("grand", grand);
		regradeLevels.put("rare", rare);
		regradeLevels.put("arcane", arcane);
		regradeLevels.put("heroic", heroic);
		regradeLevels.put("unique", unique);
		
		currentRegradeLevel = START_REGRADE_LEVEL;
	}
	
	public String nextStep(float success, float greatSuccess, float fail, float downgrade) {
		Random rng = new Random();
		int randomNumberBetweenOneAndOneThousand = rng.nextInt(1000) + 1;
		int iSuccess = (int)(success * 1000);
		int iGreatSuccess = (int)(greatSuccess * 1000);
		int iFail = (int)(fail * 1000);
		// int iDowngrade = (int)(downgrade * 1000);
		
		/*
		System.out.println("The random number: " + randomNumberBetweenOneAndOneThousand);
		System.out.println("Success range: [0 - " + iSuccess + "]");
		System.out.println("Great Success range: [" + iSuccess + " - " + (iSuccess + iGreatSuccess) + "]");
		System.out.println("Fail range: [" + (iSuccess + iGreatSuccess) + " - " + (iSuccess + iGreatSuccess + iFail) + "]");
		System.out.println("Down grade range: [" + (iSuccess + iGreatSuccess + iFail) + " - 1000]");
		*/
		
		
		if (randomNumberBetweenOneAndOneThousand < iSuccess) {
//			System.out.println("Success!");
			return "success";
		}
		else if (randomNumberBetweenOneAndOneThousand < iSuccess + iGreatSuccess) {
//			System.out.println("Great Success!");
			return "great success";
		}
		else if (randomNumberBetweenOneAndOneThousand < iSuccess + iGreatSuccess + iFail) {
//			System.out.println("Fail!");
			return "fail";
		}
		else if (randomNumberBetweenOneAndOneThousand < 1000) {
//			System.out.println("Downgrade!");
			return "downgrade";
		}
		
		return "";
	}
	
	/**
	 * Calls nextStep().
	 */
	public void levelMover(String currentRegradeLevel) {
		RegradeLevel currentLevelObject = (RegradeLevel)this.regradeLevels.get(currentRegradeLevel);
		String whereToGo = nextStep(currentLevelObject.getSuccess(),
				currentLevelObject.getGreatSuccess(),
				currentLevelObject.getFail(),
				currentLevelObject.getDowngrade());
		
		if ("success".equals(whereToGo)) {
			this.currentRegradeLevel = currentLevelObject.up();
		}
		else if ("great success".equals(whereToGo)) {
			this.currentRegradeLevel = currentLevelObject.upTwo();
		}
		else if ("fail".equals(whereToGo)) {
			// do nothing - the regrade level remains the same.
		}
		else if ("downgrade".equals(whereToGo)) {
			// We can go down 1, 2, or 3 times.
			Random rng = new Random();
			int howManyTimesToGoDown = rng.nextInt(3) + 1; // in {1, 2, 3}
			if (howManyTimesToGoDown == 1) {
				this.currentRegradeLevel = currentLevelObject.down();
			}
			else if (howManyTimesToGoDown == 2) {
				this.currentRegradeLevel = currentLevelObject.downTwo();
			}
			else if (howManyTimesToGoDown == 3) {
				this.currentRegradeLevel = currentLevelObject.downThree();
			}
		}
	}
	
	/**
	 * Called in the iterator. 
	 * Calls on levelMover().
	 */
	public void regrade() {
		this.levelMover(this.currentRegradeLevel);
	}
	
	public int regradeUntilCelestial() {
		int numberOfRegrades = 0;
		while("celestial" != this.currentRegradeLevel && "divine" != this.currentRegradeLevel) {
			// Print out what regrade level we're at.
//			System.out.println(this.currentRegradeLevel);
			// 
			regrade();
//			System.out.println("========");
			numberOfRegrades++;
		}
		this.resetRegradeLevel();
		return numberOfRegrades;
	}
	
	public void resetRegradeLevel() {
		this.currentRegradeLevel = START_REGRADE_LEVEL;
	}
	
	public void averageNumberOfRegrades(int sampleCases) {
		int totalRegrades = 0;
		for (int i = 0; i < sampleCases; i++) {
			totalRegrades += regradeUntilCelestial();
			
		}
		System.out.println("Number of items brought from Rare to Celestial: " + sampleCases);
		System.out.println("Number of regrades it took: " + totalRegrades);
		System.out.println("Average number of regrades it takes to bring an item from Rare to Celestial: " + (float)totalRegrades/sampleCases);
	}
	
	public static void main(String[] args) {

		RegradeSimulator rs = new RegradeSimulator();
//		rs.averageNumberOfRegrades(1);
		Scanner scan = new Scanner(System.in);

		boolean keepGoing = true;
		
		while(keepGoing) {
			System.out.println("Enter the number of items you would like to bring up to Celestial from " + START_REGRADE_LEVEL + " (type q to quit)");

			String stopOrNot = scan.nextLine();
			if(stopOrNot.equals("q")) {
				System.out.println("Quitting program.");
				keepGoing = false;
				break;
			}
			
			int numItems = Integer.parseInt(stopOrNot);
			rs.averageNumberOfRegrades(numItems);

		}
		
		scan.close();
	}
}
