package ploblem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProblemA {

	public static void main(String[] args) {
		try {
			File file = new File("A");
			BufferedReader br = new BufferedReader(new FileReader(file));
			File fileOut = new File("answer.txt");
			PrintWriter pw = new PrintWriter(fileOut);

			int gap = 0, gapmax, goukaku = 0;
			int m = 0;
			int nmin = 0;
			int nmax = 0;
			String str;

			while (true) {
				str = br.readLine();
				String[] strAray = str.split("\\s");

				m = Integer.parseInt(strAray[0]);
				nmin = Integer.parseInt(strAray[1]);
				nmax = Integer.parseInt(strAray[2]);

				if (m == 0 && nmin == 0 && nmax == 0)
					break;

				String[] hairetu = new String[m];
				int[] H = new int[m];
				gapmax = 0;

				for (int i = 0; i < m; i++) {
					hairetu[i] = br.readLine();
					H[i] = Integer.parseInt(hairetu[i]);
					// System.out.println(hairetu[i]); //ここがおかしい
				}
				for (int i = nmin - 1; i < nmax; i++) {
					gap = H[i] - H[i + 1];
					// System.out.println(gap); //ここもおかしい
					if (gapmax <= gap) {
						gapmax = gap;
						goukaku = i + 1;
					}
				}
				// System.out.println(gapmax);
				// System.out.println(goukaku);
				pw.println(goukaku);
			}
			pw.close();
			br.close();
		} catch (IOException e) {
			System.out.println("エラー");
			e.printStackTrace();
		}
	}
}
