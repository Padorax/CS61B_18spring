public class Piece {
	public int longitude;
	public int latitude;

	public Piece(int x, int y) {
		this.longitude = x;
		this.latitude = y;
	}
//static is necessary
	public static Piece[][] sortByLat(Piece[] p) {
		int width = (int) Math.sqrt(p.length);
		Piece[][] latSort = new Piece[width][width];
		for(int i = 0; i < p.length; i++) {
			for(int j = 0; j < latSort.length; j++) {
				if(latSort[j][0] == null) {
					latSort[j][0] = p[i];
					break;
				}
				else if(latSort[j][0].latitude == p[i].latitude) {
					int counter = 0;
					while(counter + 1 < p.length && latSort[j][counter] != null) {
						counter++;
					}
					latSort[j][counter] = p[i];
					break;
				}
			}
		}
		latSort = sortLatitudes(latSort);
		return latSort;
	}

	private static Piece[][] sortLatitudes(Piece[][] unsorted) {
		Piece[][] sorted = new Piece[unsorted.length][unsorted.length];
		int count = 0;
		while(count < unsorted.length) {
			int maximum = Integer.MIN_VALUE;
			int maxindex = 0;
			for(int i = 0; i < unsorted.length; i++) {
				if(unsorted[i] != null && unsorted[i][0].latitude > maximum) {
					maximum = unsorted[i][0].latitude;
					maxindex = i;
				}
			}
			sorted[count] = unsorted[maxindex];
			unsorted[maxindex] = null;
			count++;
		}
		return sorted;
	}

	public static Piece[][] sortFully(Piece[][] p) {
	    for (int i = 0; i < p.length; i++) {
	        Piece temp;
	        for (int j = 1; j < p.length; j++) {//same as p[i].length(null also takes up length)
	            for (int k = j; k > 0; k--) {
	                if (p[i][k]!= null && p[i][k].longitude < p[i][k-1].longitude) {
	                    temp = p[i][k];
	                    p[i][k] = p[i][k-1];
	                    p[i][k-1] = temp;
                    }
                }
            }
        }
        return p;
    }

	public static void main(String[] args) {
		Piece[] p = {new Piece(-100,30), new Piece(-25, 110), new Piece(-100, 10),
			new Piece(-50, 20), new Piece(-25, 30), new Piece(-25, 20),
			new Piece(-100, 20), new Piece(-50, 10), new Piece(-50, 30)};
		Piece[][] sp = sortByLat(p);
		Piece[][] fsp = sortFully(sp);
	}
}