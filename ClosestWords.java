
/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurssidan http://www.csc.kth.se/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se, utökad kod: Addi Djikic    */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
	LinkedList<String> closestWords = null;
	int m;
	int n;
	int closestDistance = -1;
	static int MAXLENGTH = 40;
	static int costMatrix[][] = new int[MAXLENGTH][MAXLENGTH];
	int cost;
	int M;
	int EXCEDEDLIMIT = 500;
	static {
		for (int i = 0; i < MAXLENGTH; i++) {
			costMatrix[i][0] = costMatrix[0][i] = i;
		}
	}

	/*
	 * partDist: Author: Addi Djikic
	 */
	int partDist(String w1, String w2, String prevWord) {
		int w1len = w1.length();
		int w2len = w2.length();
		int prevWordlen = prevWord.length();
		
		int ShortestofWords = w2len < prevWordlen ? w2len : prevWordlen;
		// We want to check on how many letters the previous word
		// matches with the current word
		int itrrFrom = 1;
		while (w2.charAt(itrrFrom - 1) == prevWord.charAt(itrrFrom - 1) && itrrFrom < ShortestofWords) {
			itrrFrom++; // Increment to where to start comparing for the next word
		}

		// If the cost is too high it is no point of building the matrix
		if (Math.abs(w2len - w1len) > getMinDistance() && getMinDistance() != -1) {
			return EXCEDEDLIMIT;
		}

		for (m = 1; m <= w1len; m++) {
			for (n = itrrFrom; n <= w2len; n++) {
				if (w1.charAt(m - 1) == w2.charAt(n - 1)) {
					cost = 0;
				} else {
					cost = 1;
				}
				int s = costMatrix[m][n - 1] + 1;
				int p = costMatrix[m - 1][n] + 1;
				int q = costMatrix[m - 1][n - 1] + cost;
				int minOfsp = Math.min(s, p);
				costMatrix[m][n] = Math.min(minOfsp, q);
				M = costMatrix[m][n];

			}

		}
		return M;
	}

	int Distance(String w1, String w2, String prevWord) {
		return partDist(w1, w2, prevWord);
	}

	public ClosestWords(String w, List<String> wordList) {
		String prevWord = "xxxXXXx"; // So that we keep track of the last word
										// to compare
		for (String s : wordList) {
			int dist = Distance(w, s, prevWord);
			// System.out.println("d(" + w + "," + s + ")=" + dist); //Comment later
			if (dist < closestDistance || closestDistance == -1) {
				closestDistance = dist;
				closestWords = new LinkedList<String>();
				closestWords.add(s);
			} else if (dist == closestDistance)
				closestWords.add(s);

			if (dist != EXCEDEDLIMIT) {
				prevWord = s;
			}
		}
	}

	int getMinDistance() {
		return closestDistance;
	}

	List<String> getClosestWords() {
		return closestWords;
	}
}