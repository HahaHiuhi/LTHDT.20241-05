package World;




import Organism.Organism;

public class WorldMap {
	private static int[][] map = new int[30][30];
	
	public static int[] findNearest(int x, int y, int type) {
		int [] ans = new int[2];
		ans[0] = ans[1] = -1;
		double minDistance = Double.MAX_VALUE;
		for (int i = 0;  i < World.WIDTH ; ++i) {
			for(int j = 0; j < World.HEIGHT; ++j) {
			
			if (map[i][j] == type) {
                double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
				if (distance < minDistance) {
					minDistance = distance;
					ans[0] = i;
					ans[1] = j;
				}
			} 
			}
		}

		
		return ans;
	}
	
	public static void update(int x, int y, int state) {
		if(x < 0 || y < 0 || x >= 30 || y >= 30) return;
		map[x][y] = state;
	}
	
	public static boolean isOccupied(int x, int y) {
		return x < 0 || x >= World.WIDTH || y < 0 || y >= World.HEIGHT || map[x][y] != Organism.EMPTY;
	}
	
	
	public static void reset() {
		map = new int[30][30];
	}
}
	
	



