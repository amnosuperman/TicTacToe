import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AIGrid {
	
	boolean ai = false;
	int size;
	int[][] revealed;
	boolean gameover;
	
	public AIGrid(int gsize, boolean val) {
		size = gsize;
		ai = val;
		revealed = new int[size][size];
	}
	
	protected int checkstatus() {
		int status = 0;
		int i, j;

		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				if (revealed[i][j] != 1)
					break;
			}
			if (j == size)
				status = 1;

			for (j = 0; j < size; j++) {
				if (revealed[i][j] != 2)
					break;
			}
			if (j == size)
				status = 2;
		}

		for (j = 0; j < size; j++) {
			for (i = 0; i < size; i++) {
				if (revealed[i][j] != 1)
					break;
			}
			if (i == size)
				status = 1;

			for (i = 0; i < size; i++) {
				if (revealed[i][j] != 2)
					break;
			}
			if (i == size)
				status = 2;
		}
		for (i = 0; i < size; i++) {
			if (revealed[i][i] != 1)
				break;
		}
		if (i == size) 
			status = 1;
		for (i = 0; i < size; i++) {
			if (revealed[i][i] != 2)
				break;
		}
		if (i == size)
			status = 2;

		for (i = 0; i < size; i++) {
			if (revealed[i][size - i - 1] != 1)
				break;
		}
		if (i == size)
			status = 1;
		for (i = 0; i < size; i++) {
			if (revealed[i][size - i - 1] != 2)
				break;
		}
		if (i == size)
			status = 2;
		
		if(status>0)
			gameover = true;
		
		return status;
	}
	
	int[] nextmove() {
		int[] nmove = new int[2];
		if((!ai)||(checkstatus()>0)) {
			nmove[0] = -1;
			nmove[1] = -1;
		}
		else {
			int[] x = checkoneempty();
			if(x[0]== -1)
			{
				if(revealed[size/2][size/2] == 0){
					nmove[0] = size/2;
					nmove[1] = size/2;
				}
				else if((revealed[0][0] == 1)&&(revealed[size-1][size-1] == 0)){
					nmove[0] = size-1;
					nmove[1] = size-1;
				}
				else if((revealed[size-1][size-1] == 1)&&(revealed[0][0] == 0)){
					nmove[0] = 0;
					nmove[1] = 0;
				}	
				else if((revealed[size-1][0] == 1)&&(revealed[0][size-1] == 0)){
					nmove[0] = 0;
					nmove[1] = size-1;
				}	
				else if((revealed[size-1][0] == 0)&&(revealed[0][size-1] == 1)){
					nmove[0] = size-1;
					nmove[1] = 0;
				}
				else if(revealed[0][0] == 0){
					nmove[0] = 0;
					nmove[1] = 0;
				}
				else if(revealed[size-1][0] == 0){
					nmove[0] = size-1;
					nmove[1] = 0;
				}
				else if(revealed[0][size-1] == 0){
					nmove[0] = 0;
					nmove[1] = size-1;
				}
				else if(revealed[size-1][size-1] == 0){
					nmove[0] = size-1;
					nmove[1] = size-1;
				}
				else
				{
					List<int[]> lst = new ArrayList<int[]>();
					int[] temp = new int[2];
					int i,j;
					for(i=0;i<size;i++)
					{
						for(j=0;j<size;j++)
						{
							if(revealed[i][j]==0)
							{
								temp[0] = i;
								temp[1] = j;
								lst.add(temp);
							}
						}
					}								

					Random rand = new Random();
					int idx = rand.nextInt(lst.size());
					nmove = lst.get(idx);
					lst.clear();
				}
			}
			else
				nmove = x;
			
		}
		return nmove;
	}

	int[] checkoneempty()
	{
		int i,j;
		int[] t = new int[2];
		t[0] = t[1] = -1;
		int count = 0;
		int idx = 0;
		
		for(i = 0;i<size;i++)
		{
			count = 0;
			for(j=0;j<size;j++)
			{
				if(revealed[i][j] == 2)
					count++;
				else
					idx = j;
			}
			if((count == size-1)&&(revealed[i][idx]==0))
			{
				t[0] = i;
				t[1] = idx;
				return t;
			}
		}
		
		for(i = 0;i<size;i++)
		{
			count = 0;
			for(j=0;j<size;j++)
			{
				if(revealed[j][i] == 2)
					count++;
				else
					idx = j;
			}
			if((count == size-1)&&(revealed[idx][i]==0))
			{
				t[0] = idx;
				t[1] = i;
				return t;
			}
		}
		
		for(i=0;i<size;i++)
		{
			count = 0;
			if(revealed[i][i] == 2)
				count ++;
			else
				idx = i;
		}
		if((count == size-1)&&(revealed[idx][idx]==0))
		{
			t[0] = t[1] = idx;
			return t;
		}
		
		for(i=0;i<size;i++)
		{
			count = 0;
			if(revealed[i][size-i-1] == 2)
				count ++;
			else
				idx = i;
		}
		if((count == size-1)&&(revealed[idx][size-idx-1]==0))
		{
			t[0] = idx;
			t[1] = size-1-idx;
			return t;
		}
		
		for(i = 0;i<size;i++)
		{
			count = 0;
			for(j=0;j<size;j++)
			{
				if(revealed[i][j] == 1)
					count++;
				else
					idx = j;
			}
			if((count == size-1)&&(revealed[i][idx]==0))
			{
				t[0] = i;
				t[1] = idx;
				return t;
			}
		}
		
		for(i = 0;i<size;i++)
		{
			count = 0;
			for(j=0;j<size;j++)
			{
				if(revealed[j][i] == 1)
					count++;
				else
					idx = j;
			}
			if((count == size-1)&&(revealed[idx][i]==0))
			{
				t[0] = idx;
				t[1] = i;
				return t;
			}
		}
		
		for(i=0;i<size;i++)
		{
			count = 0;
			if(revealed[i][i] == 1)
				count ++;
			else
				idx = i;
		}
		if((count == size-1)&&(revealed[idx][idx]==0))
		{
			t[0] = t[1] = idx;
			return t;
		}
		
		for(i=0;i<size;i++)
		{
			count = 0;
			if(revealed[i][size-i-1] == 1)
				count ++;
			else
				idx = i;
		}
		if((count == size-1)&&(revealed[idx][size-idx-1]==0))
		{
			t[0] = idx;
			t[1] = size-1-idx;
			return t;
		}
		
		return t;
	}
	
}
