package spreadsheet.algo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BinPack {
	static double SIZE = 6000;

	public ArrayList<Double>[] doProcess(Object data[][], int size, int targetSize) {
		
		SIZE = targetSize;
		ArrayList<Node> list = new ArrayList<>();
		int bins_cnt = 0;
		String tempinput;
		for (int i = 0; i < size; i++) {
			tempinput = ((String)data[i][1]).replace(",", "");// 컴마 빼준다.
			double len = Double.parseDouble(tempinput);
			int cnt = Integer.parseInt(((String)data[i][2]));

			bins_cnt += cnt;
			list.add(new Node(len, cnt));
		}

		Collections.sort(list, new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.len > o2.len)
					return -1;
				else if (o1.len < o2.len)
					return 1;
				else
					return 0;
			}
		});// 내림차순 정렬

		ArrayList<Double>[] bins = new ArrayList[bins_cnt];// 통
		bins[0] = new ArrayList<>();// 첫 번째 통은 먼저 만들어 준다.
		bins[0].add((double) SIZE);
		int B = 0;// 통 개수

		for (int i = 0; i < list.size(); i++) {// 리스트 순차적으로 가면서 탐색
			int list_size = list.get(i).cnt;
			for (int j = 0; j < list_size; j++) {// 각각 마디 마다 몇개가 있는지
				boolean flag = false;// 새로 통을 추가해야하는가
				for (int k = 0; k <= B; k++) {// 쓰레기통 첨부터 탐색
					if (bins[k].get(0) >= list.get(i).len) {// 해당 통에 들어갈 수 있으면 넣는다.
						double temp = bins[k].get(0) - list.get(i).len;
						bins[k].remove(0);
						bins[k].add(0, temp);
						bins[k].add(list.get(i).len);
						flag = true;
						break;// 통에 넣었으면 통 탐색을 그만둔다.
					}
				}
				if (!flag) {// 새로운 통을 추가해야한다면
					B++;
					bins[B] = new ArrayList<>();// 통 만들고
					bins[B].add((double) SIZE);// 기준값 넣고
					double temp = bins[B].get(0) - list.get(i).len;// 데이터 추가
					bins[B].remove(0);
					bins[B].add(0, temp);
					bins[B].add(list.get(i).len);
				}
			}
		}
		return bins; 
	}

	static class Node {
		double len;
		int cnt;

		public Node(double len, int cnt) {
			super();
			this.len = len;
			this.cnt = cnt;
		}
	}
}
